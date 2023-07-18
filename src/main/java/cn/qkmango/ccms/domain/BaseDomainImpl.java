package cn.qkmango.ccms.domain;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-18 11:20
 */
public class BaseDomainImpl implements BaseDomain {

    private String version;
    private String newVersion;

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
        this.newVersion = version;
    }

    @Override
    public String getNewVersion() {
        return newVersion;
    }

    @Override
    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }
}
