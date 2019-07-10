package com.github.evgdim.gcp;

import com.google.api.MetricDescriptor
import com.google.cloud.monitoring.v3.MetricServiceClient
import com.google.monitoring.v3.*
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController;
import com.google.protobuf.util.Timestamps
import com.google.monitoring.v3.Aggregation.Aligner
import org.apache.tomcat.jni.Lock.name
import com.google.monitoring.v3.ListTimeSeriesRequest
import com.google.cloud.monitoring.v3.MetricServiceClient.ListTimeSeriesPagedResponse






@RestController
@RequestMapping("/metrics")
class MetricsController {
    @GetMapping("/list")
    fun getList() : List<MetricsItem> {
        val client = MetricServiceClient.create()
        val name = ProjectName.of("gcp-evgdim-test")

        val request = ListMetricDescriptorsRequest
                .newBuilder()
                .setName(name.toString())
                .build()
        val response = client.listMetricDescriptors(request)

        return response.iterateAll().map { MetricsItem(it.name, it.displayName) }
    }

    @GetMapping
    fun get(): List<Any> {
        val client = MetricServiceClient.create()
        val name = ProjectName.of("gcp-evgdim-test")

        val startMillis = System.currentTimeMillis() - 60 * 20 * 1000
        val interval = TimeInterval.newBuilder()
                .setStartTime(Timestamps.fromMillis(startMillis))
                .setEndTime(Timestamps.fromMillis(System.currentTimeMillis()))
                .build()

        val aggregation = Aggregation.newBuilder()
                .setAlignmentPeriod(com.google.protobuf.Duration.newBuilder().setSeconds(600).build())
                .setPerSeriesAligner(Aggregation.Aligner.ALIGN_MEAN)
                .build()

        val request = ListTimeSeriesRequest.newBuilder()
                .setName(name.toString())
                .setFilter("metric.type=\"compute.googleapis.com/instance/cpu/utilization\"")
                .setInterval(interval)
                .setAggregation(aggregation)
                .build()

        val response = client
                .listTimeSeries(request)

//        println("Got timeseries: ")
//        for (ts in response.iterateAll()) {
//            System.out.println(ts)
//        }
        return response.iterateAll().iterator().asSequence().toList().map {
            CpuUsage(it.valueType.name, it.metric.type, it.resource.type, it.pointsList.joinToString{ it.toString() })
        }
    }

    data class CpuUsage(val valueType: String, val metricType: String, val resourceType: String, val pointsList: String)

    data class MetricsItem(val name: String, val displayName: String)

    @GetMapping("/resources")
    fun getResources() : List<MetricsItem> {
        val client = MetricServiceClient.create()
        val name = ProjectName.of("gcp-evgdim-test")

        val request = ListMonitoredResourceDescriptorsRequest.newBuilder()
                .setName(name.toString())
                .build()

        val response = client.listMonitoredResourceDescriptors(request)

        return response.iterateAll().map { MetricsItem(it.name, it.displayName) }
    }
}
