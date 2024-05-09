package com.ada.federate.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.31.1)",
    comments = "Source: service.proto")
public final class SeJamGrpc {

  private SeJamGrpc() {}

  public static final String SERVICE_NAME = "federate.SeJam";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.HelloRequest,
      com.ada.federate.rpc.RPCCommon.Status> getRpcHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "rpcHello",
      requestType = com.ada.federate.rpc.RPCService.HelloRequest.class,
      responseType = com.ada.federate.rpc.RPCCommon.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.HelloRequest,
      com.ada.federate.rpc.RPCCommon.Status> getRpcHelloMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.HelloRequest, com.ada.federate.rpc.RPCCommon.Status> getRpcHelloMethod;
    if ((getRpcHelloMethod = SeJamGrpc.getRpcHelloMethod) == null) {
      synchronized (SeJamGrpc.class) {
        if ((getRpcHelloMethod = SeJamGrpc.getRpcHelloMethod) == null) {
          SeJamGrpc.getRpcHelloMethod = getRpcHelloMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCService.HelloRequest, com.ada.federate.rpc.RPCCommon.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "rpcHello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCService.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.Status.getDefaultInstance()))
              .setSchemaDescriptor(new SeJamMethodDescriptorSupplier("rpcHello"))
              .build();
        }
      }
    }
    return getRpcHelloMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.QueryResult> getPubicQueryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "pubicQuery",
      requestType = com.ada.federate.rpc.RPCCommon.SQLExpression.class,
      responseType = com.ada.federate.rpc.RPCCommon.QueryResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.QueryResult> getPubicQueryMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.QueryResult> getPubicQueryMethod;
    if ((getPubicQueryMethod = SeJamGrpc.getPubicQueryMethod) == null) {
      synchronized (SeJamGrpc.class) {
        if ((getPubicQueryMethod = SeJamGrpc.getPubicQueryMethod) == null) {
          SeJamGrpc.getPubicQueryMethod = getPubicQueryMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.QueryResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "pubicQuery"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SQLExpression.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.QueryResult.getDefaultInstance()))
              .setSchemaDescriptor(new SeJamMethodDescriptorSupplier("pubicQuery"))
              .build();
        }
      }
    }
    return getPubicQueryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.PublicQueryMessage,
      com.ada.federate.rpc.RPCCommon.FinalResult> getGroupByAggregationPlaintextMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "groupByAggregationPlaintext",
      requestType = com.ada.federate.rpc.RPCCommon.PublicQueryMessage.class,
      responseType = com.ada.federate.rpc.RPCCommon.FinalResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.PublicQueryMessage,
      com.ada.federate.rpc.RPCCommon.FinalResult> getGroupByAggregationPlaintextMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.PublicQueryMessage, com.ada.federate.rpc.RPCCommon.FinalResult> getGroupByAggregationPlaintextMethod;
    if ((getGroupByAggregationPlaintextMethod = SeJamGrpc.getGroupByAggregationPlaintextMethod) == null) {
      synchronized (SeJamGrpc.class) {
        if ((getGroupByAggregationPlaintextMethod = SeJamGrpc.getGroupByAggregationPlaintextMethod) == null) {
          SeJamGrpc.getGroupByAggregationPlaintextMethod = getGroupByAggregationPlaintextMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.PublicQueryMessage, com.ada.federate.rpc.RPCCommon.FinalResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "groupByAggregationPlaintext"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.PublicQueryMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.FinalResult.getDefaultInstance()))
              .setSchemaDescriptor(new SeJamMethodDescriptorSupplier("groupByAggregationPlaintext"))
              .build();
        }
      }
    }
    return getGroupByAggregationPlaintextMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.GroupIR> getGroupByPlaintextMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "groupByPlaintext",
      requestType = com.ada.federate.rpc.RPCCommon.SQLExpression.class,
      responseType = com.ada.federate.rpc.RPCCommon.GroupIR.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.GroupIR> getGroupByPlaintextMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.GroupIR> getGroupByPlaintextMethod;
    if ((getGroupByPlaintextMethod = SeJamGrpc.getGroupByPlaintextMethod) == null) {
      synchronized (SeJamGrpc.class) {
        if ((getGroupByPlaintextMethod = SeJamGrpc.getGroupByPlaintextMethod) == null) {
          SeJamGrpc.getGroupByPlaintextMethod = getGroupByPlaintextMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.GroupIR>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "groupByPlaintext"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SQLExpression.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.GroupIR.getDefaultInstance()))
              .setSchemaDescriptor(new SeJamMethodDescriptorSupplier("groupByPlaintext"))
              .build();
        }
      }
    }
    return getGroupByPlaintextMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.Status> getGroupByADDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "groupByADD",
      requestType = com.ada.federate.rpc.RPCCommon.SQLExpression.class,
      responseType = com.ada.federate.rpc.RPCCommon.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.Status> getGroupByADDMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.Status> getGroupByADDMethod;
    if ((getGroupByADDMethod = SeJamGrpc.getGroupByADDMethod) == null) {
      synchronized (SeJamGrpc.class) {
        if ((getGroupByADDMethod = SeJamGrpc.getGroupByADDMethod) == null) {
          SeJamGrpc.getGroupByADDMethod = getGroupByADDMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "groupByADD"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SQLExpression.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.Status.getDefaultInstance()))
              .setSchemaDescriptor(new SeJamMethodDescriptorSupplier("groupByADD"))
              .build();
        }
      }
    }
    return getGroupByADDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.GroupIR> getGroupByORDERMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "groupByORDER",
      requestType = com.ada.federate.rpc.RPCCommon.SQLExpression.class,
      responseType = com.ada.federate.rpc.RPCCommon.GroupIR.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.GroupIR> getGroupByORDERMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.GroupIR> getGroupByORDERMethod;
    if ((getGroupByORDERMethod = SeJamGrpc.getGroupByORDERMethod) == null) {
      synchronized (SeJamGrpc.class) {
        if ((getGroupByORDERMethod = SeJamGrpc.getGroupByORDERMethod) == null) {
          SeJamGrpc.getGroupByORDERMethod = getGroupByORDERMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.GroupIR>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "groupByORDER"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SQLExpression.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.GroupIR.getDefaultInstance()))
              .setSchemaDescriptor(new SeJamMethodDescriptorSupplier("groupByORDER"))
              .build();
        }
      }
    }
    return getGroupByORDERMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.FinalResult> getAggregationADDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "aggregationADD",
      requestType = com.ada.federate.rpc.RPCCommon.SQLExpression.class,
      responseType = com.ada.federate.rpc.RPCCommon.FinalResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.FinalResult> getAggregationADDMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.FinalResult> getAggregationADDMethod;
    if ((getAggregationADDMethod = SeJamGrpc.getAggregationADDMethod) == null) {
      synchronized (SeJamGrpc.class) {
        if ((getAggregationADDMethod = SeJamGrpc.getAggregationADDMethod) == null) {
          SeJamGrpc.getAggregationADDMethod = getAggregationADDMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.FinalResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "aggregationADD"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SQLExpression.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.FinalResult.getDefaultInstance()))
              .setSchemaDescriptor(new SeJamMethodDescriptorSupplier("aggregationADD"))
              .build();
        }
      }
    }
    return getAggregationADDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.RawIR> getAggregationORDERMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "aggregationORDER",
      requestType = com.ada.federate.rpc.RPCCommon.SQLExpression.class,
      responseType = com.ada.federate.rpc.RPCCommon.RawIR.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.RawIR> getAggregationORDERMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.RawIR> getAggregationORDERMethod;
    if ((getAggregationORDERMethod = SeJamGrpc.getAggregationORDERMethod) == null) {
      synchronized (SeJamGrpc.class) {
        if ((getAggregationORDERMethod = SeJamGrpc.getAggregationORDERMethod) == null) {
          SeJamGrpc.getAggregationORDERMethod = getAggregationORDERMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.RawIR>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "aggregationORDER"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SQLExpression.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.RawIR.getDefaultInstance()))
              .setSchemaDescriptor(new SeJamMethodDescriptorSupplier("aggregationORDER"))
              .build();
        }
      }
    }
    return getAggregationORDERMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.RawIR,
      com.ada.federate.rpc.RPCCommon.AggregatedIR> getEncryptedAggregateADDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "encryptedAggregateADD",
      requestType = com.ada.federate.rpc.RPCCommon.RawIR.class,
      responseType = com.ada.federate.rpc.RPCCommon.AggregatedIR.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.RawIR,
      com.ada.federate.rpc.RPCCommon.AggregatedIR> getEncryptedAggregateADDMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.RawIR, com.ada.federate.rpc.RPCCommon.AggregatedIR> getEncryptedAggregateADDMethod;
    if ((getEncryptedAggregateADDMethod = SeJamGrpc.getEncryptedAggregateADDMethod) == null) {
      synchronized (SeJamGrpc.class) {
        if ((getEncryptedAggregateADDMethod = SeJamGrpc.getEncryptedAggregateADDMethod) == null) {
          SeJamGrpc.getEncryptedAggregateADDMethod = getEncryptedAggregateADDMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.RawIR, com.ada.federate.rpc.RPCCommon.AggregatedIR>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "encryptedAggregateADD"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.RawIR.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.AggregatedIR.getDefaultInstance()))
              .setSchemaDescriptor(new SeJamMethodDescriptorSupplier("encryptedAggregateADD"))
              .build();
        }
      }
    }
    return getEncryptedAggregateADDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.AggregatedIR,
      com.ada.federate.rpc.RPCCommon.FinalResult> getDecryptAggregatedORDERMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "decryptAggregatedORDER",
      requestType = com.ada.federate.rpc.RPCCommon.AggregatedIR.class,
      responseType = com.ada.federate.rpc.RPCCommon.FinalResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.AggregatedIR,
      com.ada.federate.rpc.RPCCommon.FinalResult> getDecryptAggregatedORDERMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.AggregatedIR, com.ada.federate.rpc.RPCCommon.FinalResult> getDecryptAggregatedORDERMethod;
    if ((getDecryptAggregatedORDERMethod = SeJamGrpc.getDecryptAggregatedORDERMethod) == null) {
      synchronized (SeJamGrpc.class) {
        if ((getDecryptAggregatedORDERMethod = SeJamGrpc.getDecryptAggregatedORDERMethod) == null) {
          SeJamGrpc.getDecryptAggregatedORDERMethod = getDecryptAggregatedORDERMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.AggregatedIR, com.ada.federate.rpc.RPCCommon.FinalResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "decryptAggregatedORDER"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.AggregatedIR.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.FinalResult.getDefaultInstance()))
              .setSchemaDescriptor(new SeJamMethodDescriptorSupplier("decryptAggregatedORDER"))
              .build();
        }
      }
    }
    return getDecryptAggregatedORDERMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SeJamStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SeJamStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SeJamStub>() {
        @java.lang.Override
        public SeJamStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SeJamStub(channel, callOptions);
        }
      };
    return SeJamStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SeJamBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SeJamBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SeJamBlockingStub>() {
        @java.lang.Override
        public SeJamBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SeJamBlockingStub(channel, callOptions);
        }
      };
    return SeJamBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SeJamFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SeJamFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SeJamFutureStub>() {
        @java.lang.Override
        public SeJamFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SeJamFutureStub(channel, callOptions);
        }
      };
    return SeJamFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SeJamImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Initialization work: add client、
     * </pre>
     */
    public void rpcHello(com.ada.federate.rpc.RPCService.HelloRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcHelloMethod(), responseObserver);
    }

    /**
     */
    public void pubicQuery(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.QueryResult> responseObserver) {
      asyncUnimplementedUnaryCall(getPubicQueryMethod(), responseObserver);
    }

    /**
     */
    public void groupByAggregationPlaintext(com.ada.federate.rpc.RPCCommon.PublicQueryMessage request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.FinalResult> responseObserver) {
      asyncUnimplementedUnaryCall(getGroupByAggregationPlaintextMethod(), responseObserver);
    }

    /**
     */
    public void groupByPlaintext(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.GroupIR> responseObserver) {
      asyncUnimplementedUnaryCall(getGroupByPlaintextMethod(), responseObserver);
    }

    /**
     */
    public void groupByADD(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getGroupByADDMethod(), responseObserver);
    }

    /**
     */
    public void groupByORDER(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.GroupIR> responseObserver) {
      asyncUnimplementedUnaryCall(getGroupByORDERMethod(), responseObserver);
    }

    /**
     */
    public void aggregationADD(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.FinalResult> responseObserver) {
      asyncUnimplementedUnaryCall(getAggregationADDMethod(), responseObserver);
    }

    /**
     */
    public void aggregationORDER(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.RawIR> responseObserver) {
      asyncUnimplementedUnaryCall(getAggregationORDERMethod(), responseObserver);
    }

    /**
     */
    public void encryptedAggregateADD(com.ada.federate.rpc.RPCCommon.RawIR request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.AggregatedIR> responseObserver) {
      asyncUnimplementedUnaryCall(getEncryptedAggregateADDMethod(), responseObserver);
    }

    /**
     */
    public void decryptAggregatedORDER(com.ada.federate.rpc.RPCCommon.AggregatedIR request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.FinalResult> responseObserver) {
      asyncUnimplementedUnaryCall(getDecryptAggregatedORDERMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRpcHelloMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCService.HelloRequest,
                com.ada.federate.rpc.RPCCommon.Status>(
                  this, METHODID_RPC_HELLO)))
          .addMethod(
            getPubicQueryMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.SQLExpression,
                com.ada.federate.rpc.RPCCommon.QueryResult>(
                  this, METHODID_PUBIC_QUERY)))
          .addMethod(
            getGroupByAggregationPlaintextMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.PublicQueryMessage,
                com.ada.federate.rpc.RPCCommon.FinalResult>(
                  this, METHODID_GROUP_BY_AGGREGATION_PLAINTEXT)))
          .addMethod(
            getGroupByPlaintextMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.SQLExpression,
                com.ada.federate.rpc.RPCCommon.GroupIR>(
                  this, METHODID_GROUP_BY_PLAINTEXT)))
          .addMethod(
            getGroupByADDMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.SQLExpression,
                com.ada.federate.rpc.RPCCommon.Status>(
                  this, METHODID_GROUP_BY_ADD)))
          .addMethod(
            getGroupByORDERMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.SQLExpression,
                com.ada.federate.rpc.RPCCommon.GroupIR>(
                  this, METHODID_GROUP_BY_ORDER)))
          .addMethod(
            getAggregationADDMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.SQLExpression,
                com.ada.federate.rpc.RPCCommon.FinalResult>(
                  this, METHODID_AGGREGATION_ADD)))
          .addMethod(
            getAggregationORDERMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.SQLExpression,
                com.ada.federate.rpc.RPCCommon.RawIR>(
                  this, METHODID_AGGREGATION_ORDER)))
          .addMethod(
            getEncryptedAggregateADDMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.RawIR,
                com.ada.federate.rpc.RPCCommon.AggregatedIR>(
                  this, METHODID_ENCRYPTED_AGGREGATE_ADD)))
          .addMethod(
            getDecryptAggregatedORDERMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.AggregatedIR,
                com.ada.federate.rpc.RPCCommon.FinalResult>(
                  this, METHODID_DECRYPT_AGGREGATED_ORDER)))
          .build();
    }
  }

  /**
   */
  public static final class SeJamStub extends io.grpc.stub.AbstractAsyncStub<SeJamStub> {
    private SeJamStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SeJamStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SeJamStub(channel, callOptions);
    }

    /**
     * <pre>
     * Initialization work: add client、
     * </pre>
     */
    public void rpcHello(com.ada.federate.rpc.RPCService.HelloRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcHelloMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void pubicQuery(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.QueryResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPubicQueryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void groupByAggregationPlaintext(com.ada.federate.rpc.RPCCommon.PublicQueryMessage request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.FinalResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGroupByAggregationPlaintextMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void groupByPlaintext(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.GroupIR> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGroupByPlaintextMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void groupByADD(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGroupByADDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void groupByORDER(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.GroupIR> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGroupByORDERMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void aggregationADD(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.FinalResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAggregationADDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void aggregationORDER(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.RawIR> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAggregationORDERMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void encryptedAggregateADD(com.ada.federate.rpc.RPCCommon.RawIR request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.AggregatedIR> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getEncryptedAggregateADDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void decryptAggregatedORDER(com.ada.federate.rpc.RPCCommon.AggregatedIR request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.FinalResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDecryptAggregatedORDERMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SeJamBlockingStub extends io.grpc.stub.AbstractBlockingStub<SeJamBlockingStub> {
    private SeJamBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SeJamBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SeJamBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Initialization work: add client、
     * </pre>
     */
    public com.ada.federate.rpc.RPCCommon.Status rpcHello(com.ada.federate.rpc.RPCService.HelloRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcHelloMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.QueryResult pubicQuery(com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return blockingUnaryCall(
          getChannel(), getPubicQueryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.FinalResult groupByAggregationPlaintext(com.ada.federate.rpc.RPCCommon.PublicQueryMessage request) {
      return blockingUnaryCall(
          getChannel(), getGroupByAggregationPlaintextMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.GroupIR groupByPlaintext(com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return blockingUnaryCall(
          getChannel(), getGroupByPlaintextMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.Status groupByADD(com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return blockingUnaryCall(
          getChannel(), getGroupByADDMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.GroupIR groupByORDER(com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return blockingUnaryCall(
          getChannel(), getGroupByORDERMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.FinalResult aggregationADD(com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return blockingUnaryCall(
          getChannel(), getAggregationADDMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.RawIR aggregationORDER(com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return blockingUnaryCall(
          getChannel(), getAggregationORDERMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.AggregatedIR encryptedAggregateADD(com.ada.federate.rpc.RPCCommon.RawIR request) {
      return blockingUnaryCall(
          getChannel(), getEncryptedAggregateADDMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.FinalResult decryptAggregatedORDER(com.ada.federate.rpc.RPCCommon.AggregatedIR request) {
      return blockingUnaryCall(
          getChannel(), getDecryptAggregatedORDERMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SeJamFutureStub extends io.grpc.stub.AbstractFutureStub<SeJamFutureStub> {
    private SeJamFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SeJamFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SeJamFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Initialization work: add client、
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.Status> rpcHello(
        com.ada.federate.rpc.RPCService.HelloRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcHelloMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.QueryResult> pubicQuery(
        com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return futureUnaryCall(
          getChannel().newCall(getPubicQueryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.FinalResult> groupByAggregationPlaintext(
        com.ada.federate.rpc.RPCCommon.PublicQueryMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGroupByAggregationPlaintextMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.GroupIR> groupByPlaintext(
        com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return futureUnaryCall(
          getChannel().newCall(getGroupByPlaintextMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.Status> groupByADD(
        com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return futureUnaryCall(
          getChannel().newCall(getGroupByADDMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.GroupIR> groupByORDER(
        com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return futureUnaryCall(
          getChannel().newCall(getGroupByORDERMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.FinalResult> aggregationADD(
        com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return futureUnaryCall(
          getChannel().newCall(getAggregationADDMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.RawIR> aggregationORDER(
        com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return futureUnaryCall(
          getChannel().newCall(getAggregationORDERMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.AggregatedIR> encryptedAggregateADD(
        com.ada.federate.rpc.RPCCommon.RawIR request) {
      return futureUnaryCall(
          getChannel().newCall(getEncryptedAggregateADDMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.FinalResult> decryptAggregatedORDER(
        com.ada.federate.rpc.RPCCommon.AggregatedIR request) {
      return futureUnaryCall(
          getChannel().newCall(getDecryptAggregatedORDERMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RPC_HELLO = 0;
  private static final int METHODID_PUBIC_QUERY = 1;
  private static final int METHODID_GROUP_BY_AGGREGATION_PLAINTEXT = 2;
  private static final int METHODID_GROUP_BY_PLAINTEXT = 3;
  private static final int METHODID_GROUP_BY_ADD = 4;
  private static final int METHODID_GROUP_BY_ORDER = 5;
  private static final int METHODID_AGGREGATION_ADD = 6;
  private static final int METHODID_AGGREGATION_ORDER = 7;
  private static final int METHODID_ENCRYPTED_AGGREGATE_ADD = 8;
  private static final int METHODID_DECRYPT_AGGREGATED_ORDER = 9;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SeJamImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SeJamImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RPC_HELLO:
          serviceImpl.rpcHello((com.ada.federate.rpc.RPCService.HelloRequest) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status>) responseObserver);
          break;
        case METHODID_PUBIC_QUERY:
          serviceImpl.pubicQuery((com.ada.federate.rpc.RPCCommon.SQLExpression) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.QueryResult>) responseObserver);
          break;
        case METHODID_GROUP_BY_AGGREGATION_PLAINTEXT:
          serviceImpl.groupByAggregationPlaintext((com.ada.federate.rpc.RPCCommon.PublicQueryMessage) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.FinalResult>) responseObserver);
          break;
        case METHODID_GROUP_BY_PLAINTEXT:
          serviceImpl.groupByPlaintext((com.ada.federate.rpc.RPCCommon.SQLExpression) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.GroupIR>) responseObserver);
          break;
        case METHODID_GROUP_BY_ADD:
          serviceImpl.groupByADD((com.ada.federate.rpc.RPCCommon.SQLExpression) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status>) responseObserver);
          break;
        case METHODID_GROUP_BY_ORDER:
          serviceImpl.groupByORDER((com.ada.federate.rpc.RPCCommon.SQLExpression) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.GroupIR>) responseObserver);
          break;
        case METHODID_AGGREGATION_ADD:
          serviceImpl.aggregationADD((com.ada.federate.rpc.RPCCommon.SQLExpression) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.FinalResult>) responseObserver);
          break;
        case METHODID_AGGREGATION_ORDER:
          serviceImpl.aggregationORDER((com.ada.federate.rpc.RPCCommon.SQLExpression) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.RawIR>) responseObserver);
          break;
        case METHODID_ENCRYPTED_AGGREGATE_ADD:
          serviceImpl.encryptedAggregateADD((com.ada.federate.rpc.RPCCommon.RawIR) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.AggregatedIR>) responseObserver);
          break;
        case METHODID_DECRYPT_AGGREGATED_ORDER:
          serviceImpl.decryptAggregatedORDER((com.ada.federate.rpc.RPCCommon.AggregatedIR) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.FinalResult>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SeJamBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SeJamBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.ada.federate.rpc.RPCService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SeJam");
    }
  }

  private static final class SeJamFileDescriptorSupplier
      extends SeJamBaseDescriptorSupplier {
    SeJamFileDescriptorSupplier() {}
  }

  private static final class SeJamMethodDescriptorSupplier
      extends SeJamBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SeJamMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SeJamGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SeJamFileDescriptorSupplier())
              .addMethod(getRpcHelloMethod())
              .addMethod(getPubicQueryMethod())
              .addMethod(getGroupByAggregationPlaintextMethod())
              .addMethod(getGroupByPlaintextMethod())
              .addMethod(getGroupByADDMethod())
              .addMethod(getGroupByORDERMethod())
              .addMethod(getAggregationADDMethod())
              .addMethod(getAggregationORDERMethod())
              .addMethod(getEncryptedAggregateADDMethod())
              .addMethod(getDecryptAggregatedORDERMethod())
              .build();
        }
      }
    }
    return result;
  }
}
