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

	val client = MetricServiceClient.create()
	val name = ProjectName.of("my-project")

	val request = ListMetricDescriptorsRequest
			.newBuilder()
			.setName(name.toString())
			.build()
	val response = client.listMetricDescriptors(request)

	for (d in response.iterateAll()) {
		println(d.name + " " + d.displayName)
	}
}
