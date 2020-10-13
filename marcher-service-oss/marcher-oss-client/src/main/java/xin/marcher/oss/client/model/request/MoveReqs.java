package xin.marcher.oss.client.model.request;

import lombok.Data;

import java.util.List;

/**
 * 资源迁移请求
 *
 * @author marcher
 */
@Data
public class MoveReqs {

    /**
     * 源 url
     */
    private String srcUrl;

    /**
     * 路径目录集合
     */
    private List<String> directoryList;
}
