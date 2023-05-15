package com.vmware.tanzu.tap.accelerators.springbootfrontend;

class Info {
    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String javaVersion;
    private final String springBootVersion;
    private final String springBootProfiles;
    private final String ipAddress;

    Info(String groupId, String artifactId, String version, String javaVersion, String springBootVersion, String springBootProfiles, String ipAddress) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.javaVersion = javaVersion;
        this.springBootVersion = springBootVersion;
        this.springBootProfiles = springBootProfiles;
        this.ipAddress = ipAddress;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public String getSpringBootVersion() {
        return springBootVersion;
    }

    public String getSpringBootProfiles() {
        return springBootProfiles;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
