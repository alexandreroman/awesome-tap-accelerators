const path = require('path')

const otel = require('@opentelemetry/sdk-node')
const { HttpInstrumentation } = require('@opentelemetry/instrumentation-http')
const { ExpressInstrumentation } = require('@opentelemetry/instrumentation-express')
const { ZipkinExporter } = require('@opentelemetry/exporter-zipkin')
const { Resource } = require('@opentelemetry/resources')
const { SemanticResourceAttributes } = require('@opentelemetry/semantic-conventions')
const {
  BatchSpanProcessor,
  SimpleSpanProcessor,
  ParentBasedSampler,
  TraceIdRatioBasedSampler,
  AlwaysOnSampler
} = require('@opentelemetry/sdk-trace-base')
const { PeriodicExportingMetricReader } = require('@opentelemetry/sdk-metrics')
const { OTLPMetricExporter } = require('@opentelemetry/exporter-metrics-otlp-proto')

console.log("Enabling OpenTelemetry support")

const traceExporter = new ZipkinExporter({
  url: 'http://localhost:9411/api/v2/spans',
})

const metricReader = new PeriodicExportingMetricReader({
  exporter: new OTLPMetricExporter({
    url: 'http://localhost:4318/v1/metrics',
  }),
})

const pkg = require(path.join(__dirname, '/package.json'))
const serviceInstanceId = Math.floor(Math.random() * Number.MAX_SAFE_INTEGER)
const resource =
  Resource.default().merge(
    new Resource({
      [SemanticResourceAttributes.SERVICE_NAME]: pkg.name,
      [SemanticResourceAttributes.SERVICE_VERSION]: pkg.version,
      [SemanticResourceAttributes.SERVICE_INSTANCE_ID]: pkg.name + '-' + serviceInstanceId,
    })
  )

const sdk = new otel.NodeSDK({
  resource: resource,
  traceExporter: traceExporter,
  metricReader: metricReader,
  instrumentations: [
    HttpInstrumentation,
    ExpressInstrumentation
  ],
  spanProcessor: process.env.NODE_ENV === 'development'
        ? new SimpleSpanProcessor(traceExporter)
        : new BatchSpanProcessor(traceExporter),
  sampler: process.env.NODE_ENV === 'development'
        ? new AlwaysOnSampler()
        : new ParentBasedSampler({
            root: new TraceIdRatioBasedSampler(0.1)
        }),
})
sdk.start()

process.on('SIGTERM', () => {
  sdk.shutdown()
})
