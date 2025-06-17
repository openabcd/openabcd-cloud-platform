package io.github.openabcd.cloud.serverservice.apm;

import io.opentelemetry.api.trace.Span;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class TracingHttpFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        val span = Span.current();

        val wrappedRequest = new CachedBodyHttpServletRequest(request);
        val body = new String(wrappedRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        span.setAttribute("http.request.body", body);

        val wrappedResponse = new CachedBodyHttpServletResponse(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        val responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
        span.setAttribute("http.response.body", responseBody);

        response.getOutputStream().write(wrappedResponse.getContentAsByteArray());
    }

    /**
     * 트레이싱이나 로깅 목적에서 본문을 읽고 다시 필터 체인에 전달하는 목적
     * HttpServletRequest.getInputStream()은 한 번만 읽을 수 있기 때문에
     */
    public static class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

        private final byte[] cachedBody;

        public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
            super(request);
            val requestInputStream = request.getInputStream();
            this.cachedBody = requestInputStream.readAllBytes();
        }

        @Override
        public ServletInputStream getInputStream() {
            val byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);

            return new ServletInputStream() {
                @Override
                public int read() {
                    return byteArrayInputStream.read();
                }

                @Override public boolean isFinished() { return byteArrayInputStream.available() == 0; }
                @Override public boolean isReady() { return true; }
                @Override
                public void setReadListener(ReadListener readListener) {
                    // Spring MVC Not supported for sync request handling
                    throw new UnsupportedOperationException("Not supported for sync request handling");
                }
            };
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
    }

    public static class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

        private final ByteArrayOutputStream cachedBody = new ByteArrayOutputStream();
        private final PrintWriter writer = new PrintWriter(cachedBody);

        public CachedBodyHttpServletResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return new ServletOutputStream() {
                @Override
                public boolean isReady() { return true; }
                @Override
                public void setWriteListener(WriteListener listener) {
                    // Spring MVC Not supported for sync request handling
                    throw new UnsupportedOperationException("Not supported for sync request handling");
                }
                @Override
                public void write(int b) {
                    cachedBody.write(b);
                }
            };
        }

        public byte[] getContentAsByteArray() {
            writer.flush(); // ensure all data is written
            return cachedBody.toByteArray();
        }
    }
}
