package cn.qkmango.ccms.domain;

import java.io.Serializable;

/**
 * Domain 基类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-04-26 22:24
 */
public interface BaseDomain extends Serializable {

    String getVersion();

    void setVersion(String version);

    String getNewVersion();

    void setNewVersion(String newVersion);

}
