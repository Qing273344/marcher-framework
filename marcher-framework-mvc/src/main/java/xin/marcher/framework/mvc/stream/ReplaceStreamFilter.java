package xin.marcher.framework.mvc.stream;

import lombok.extern.slf4j.Slf4j;
import xin.marcher.framework.mvc.utils.WebHelp;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 替换 HttpServletRequest 实现输入流可重复读
 *
 * @author marcher
 */
@Slf4j
public class ReplaceStreamFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest servletRequest = null;

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest  = (HttpServletRequest) request;

            if (WebHelp.isFormPost(httpServletRequest)) {
                servletRequest = new RequestWrapper(httpServletRequest, true);
            } else if (WebHelp.isPost(httpServletRequest)) {
                // 文件上传, 不缓存
                //  request.getContentType().contains(MediaType.MULTIPART_FORM_DATA_VALUE)
                if (WebHelp.isMultipart(httpServletRequest)) {
                    log.info("文件上传操作不重写流");
                } else {
                    servletRequest = new RequestWrapper(httpServletRequest);
                }
            }
        }

        // 获取请求中的流，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
        // 在 chain.doFiler 方法中传递新的request对象
        if (servletRequest == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(servletRequest, response);
        }

    }

    @Override
    public void destroy() {
        log.info("StreamFilter销毁...");
    }

}
