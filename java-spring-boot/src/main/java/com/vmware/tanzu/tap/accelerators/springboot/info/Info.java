package com.vmware.tanzu.tap.accelerators.springboot.info;

record Info(
        String groupId,
        String artifactId,
        String version,
        String javaVersion,
        String springBootVersion,
        String springBootProfiles,
        String ipAddress,
        boolean runningInKubernetes
) {
}
