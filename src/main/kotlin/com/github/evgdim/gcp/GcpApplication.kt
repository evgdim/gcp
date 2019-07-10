package com.github.evgdim.gcp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.google.cloud.monitoring.v3.MetricServiceClient
import com.google.monitoring.v3.ProjectName
import com.google.cloud.monitoring.v3.MetricServiceClient.ListMetricDescriptorsPagedResponse
import org.apache.tomcat.jni.Lock.name
import com.google.monitoring.v3.ListMetricDescriptorsRequest







@SpringBootApplication
class GcpApplication

fun main(args: Array<String>) {
	runApplication<GcpApplication>(*args)


}
