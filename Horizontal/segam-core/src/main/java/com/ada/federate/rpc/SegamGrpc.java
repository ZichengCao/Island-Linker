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
public final class SegamGrpc {

  private SegamGrpc() {}

  public static final String SERVICE_NAME = "federate.Segam";

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
    if ((getRpcHelloMethod = SegamGrpc.getRpcHelloMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getRpcHelloMethod = SegamGrpc.getRpcHelloMethod) == null) {
          SegamGrpc.getRpcHelloMethod = getRpcHelloMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCService.HelloRequest, com.ada.federate.rpc.RPCCommon.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "rpcHello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCService.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.Status.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("rpcHello"))
              .build();
        }
      }
    }
    return getRpcHelloMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.PingRequest,
      com.ada.federate.rpc.RPCCommon.Status> getRpcPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "rpcPing",
      requestType = com.ada.federate.rpc.RPCService.PingRequest.class,
      responseType = com.ada.federate.rpc.RPCCommon.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.PingRequest,
      com.ada.federate.rpc.RPCCommon.Status> getRpcPingMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.PingRequest, com.ada.federate.rpc.RPCCommon.Status> getRpcPingMethod;
    if ((getRpcPingMethod = SegamGrpc.getRpcPingMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getRpcPingMethod = SegamGrpc.getRpcPingMethod) == null) {
          SegamGrpc.getRpcPingMethod = getRpcPingMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCService.PingRequest, com.ada.federate.rpc.RPCCommon.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "rpcPing"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCService.PingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.Status.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("rpcPing"))
              .build();
        }
      }
    }
    return getRpcPingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLMessage,
      com.ada.federate.rpc.RPCCommon.Status> getBatchQueryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "batchQuery",
      requestType = com.ada.federate.rpc.RPCCommon.SQLMessage.class,
      responseType = com.ada.federate.rpc.RPCCommon.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLMessage,
      com.ada.federate.rpc.RPCCommon.Status> getBatchQueryMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLMessage, com.ada.federate.rpc.RPCCommon.Status> getBatchQueryMethod;
    if ((getBatchQueryMethod = SegamGrpc.getBatchQueryMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getBatchQueryMethod = SegamGrpc.getBatchQueryMethod) == null) {
          SegamGrpc.getBatchQueryMethod = getBatchQueryMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.SQLMessage, com.ada.federate.rpc.RPCCommon.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "batchQuery"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SQLMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.Status.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("batchQuery"))
              .build();
        }
      }
    }
    return getBatchQueryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.RPCResult> getPubicQueryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "pubicQuery",
      requestType = com.ada.federate.rpc.RPCCommon.SQLExpression.class,
      responseType = com.ada.federate.rpc.RPCCommon.RPCResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.RPCResult> getPubicQueryMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.RPCResult> getPubicQueryMethod;
    if ((getPubicQueryMethod = SegamGrpc.getPubicQueryMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getPubicQueryMethod = SegamGrpc.getPubicQueryMethod) == null) {
          SegamGrpc.getPubicQueryMethod = getPubicQueryMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.RPCResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "pubicQuery"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SQLExpression.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.RPCResult.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("pubicQuery"))
              .build();
        }
      }
    }
    return getPubicQueryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.Status> getPrivateQueryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "privateQuery",
      requestType = com.ada.federate.rpc.RPCCommon.SQLExpression.class,
      responseType = com.ada.federate.rpc.RPCCommon.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression,
      com.ada.federate.rpc.RPCCommon.Status> getPrivateQueryMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.Status> getPrivateQueryMethod;
    if ((getPrivateQueryMethod = SegamGrpc.getPrivateQueryMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getPrivateQueryMethod = SegamGrpc.getPrivateQueryMethod) == null) {
          SegamGrpc.getPrivateQueryMethod = getPrivateQueryMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.SQLExpression, com.ada.federate.rpc.RPCCommon.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "privateQuery"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SQLExpression.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.Status.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("privateQuery"))
              .build();
        }
      }
    }
    return getPrivateQueryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.SetUnionRequest,
      com.ada.federate.rpc.RPCService.SetUnionResponse> getPrivateSetUnionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "privateSetUnion",
      requestType = com.ada.federate.rpc.RPCService.SetUnionRequest.class,
      responseType = com.ada.federate.rpc.RPCService.SetUnionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.SetUnionRequest,
      com.ada.federate.rpc.RPCService.SetUnionResponse> getPrivateSetUnionMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.SetUnionRequest, com.ada.federate.rpc.RPCService.SetUnionResponse> getPrivateSetUnionMethod;
    if ((getPrivateSetUnionMethod = SegamGrpc.getPrivateSetUnionMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getPrivateSetUnionMethod = SegamGrpc.getPrivateSetUnionMethod) == null) {
          SegamGrpc.getPrivateSetUnionMethod = getPrivateSetUnionMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCService.SetUnionRequest, com.ada.federate.rpc.RPCService.SetUnionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "privateSetUnion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCService.SetUnionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCService.SetUnionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("privateSetUnion"))
              .build();
        }
      }
    }
    return getPrivateSetUnionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.GroupByMaxRequest,
      com.ada.federate.rpc.RPCCommon.Status> getSecureGroupByMaxMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "secureGroupByMax",
      requestType = com.ada.federate.rpc.RPCService.GroupByMaxRequest.class,
      responseType = com.ada.federate.rpc.RPCCommon.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.GroupByMaxRequest,
      com.ada.federate.rpc.RPCCommon.Status> getSecureGroupByMaxMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.GroupByMaxRequest, com.ada.federate.rpc.RPCCommon.Status> getSecureGroupByMaxMethod;
    if ((getSecureGroupByMaxMethod = SegamGrpc.getSecureGroupByMaxMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getSecureGroupByMaxMethod = SegamGrpc.getSecureGroupByMaxMethod) == null) {
          SegamGrpc.getSecureGroupByMaxMethod = getSecureGroupByMaxMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCService.GroupByMaxRequest, com.ada.federate.rpc.RPCCommon.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "secureGroupByMax"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCService.GroupByMaxRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.Status.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("secureGroupByMax"))
              .build();
        }
      }
    }
    return getSecureGroupByMaxMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.GroupByMedianRequest,
      com.ada.federate.rpc.RPCCommon.Status> getSecureGroupByMedianMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "secureGroupByMedian",
      requestType = com.ada.federate.rpc.RPCService.GroupByMedianRequest.class,
      responseType = com.ada.federate.rpc.RPCCommon.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.GroupByMedianRequest,
      com.ada.federate.rpc.RPCCommon.Status> getSecureGroupByMedianMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.GroupByMedianRequest, com.ada.federate.rpc.RPCCommon.Status> getSecureGroupByMedianMethod;
    if ((getSecureGroupByMedianMethod = SegamGrpc.getSecureGroupByMedianMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getSecureGroupByMedianMethod = SegamGrpc.getSecureGroupByMedianMethod) == null) {
          SegamGrpc.getSecureGroupByMedianMethod = getSecureGroupByMedianMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCService.GroupByMedianRequest, com.ada.federate.rpc.RPCCommon.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "secureGroupByMedian"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCService.GroupByMedianRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.Status.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("secureGroupByMedian"))
              .build();
        }
      }
    }
    return getSecureGroupByMedianMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.GroupBySumSSRequest,
      com.ada.federate.rpc.RPCCommon.SSMessage> getSecureGroupBySumSSMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "secureGroupBySumSS",
      requestType = com.ada.federate.rpc.RPCService.GroupBySumSSRequest.class,
      responseType = com.ada.federate.rpc.RPCCommon.SSMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.GroupBySumSSRequest,
      com.ada.federate.rpc.RPCCommon.SSMessage> getSecureGroupBySumSSMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.GroupBySumSSRequest, com.ada.federate.rpc.RPCCommon.SSMessage> getSecureGroupBySumSSMethod;
    if ((getSecureGroupBySumSSMethod = SegamGrpc.getSecureGroupBySumSSMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getSecureGroupBySumSSMethod = SegamGrpc.getSecureGroupBySumSSMethod) == null) {
          SegamGrpc.getSecureGroupBySumSSMethod = getSecureGroupBySumSSMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCService.GroupBySumSSRequest, com.ada.federate.rpc.RPCCommon.SSMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "secureGroupBySumSS"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCService.GroupBySumSSRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SSMessage.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("secureGroupBySumSS"))
              .build();
        }
      }
    }
    return getSecureGroupBySumSSMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SSMessage,
      com.ada.federate.rpc.RPCCommon.Status> getSendSharingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendSharing",
      requestType = com.ada.federate.rpc.RPCCommon.SSMessage.class,
      responseType = com.ada.federate.rpc.RPCCommon.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SSMessage,
      com.ada.federate.rpc.RPCCommon.Status> getSendSharingMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCCommon.SSMessage, com.ada.federate.rpc.RPCCommon.Status> getSendSharingMethod;
    if ((getSendSharingMethod = SegamGrpc.getSendSharingMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getSendSharingMethod = SegamGrpc.getSendSharingMethod) == null) {
          SegamGrpc.getSendSharingMethod = getSendSharingMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCCommon.SSMessage, com.ada.federate.rpc.RPCCommon.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendSharing"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.SSMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCCommon.Status.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("sendSharing"))
              .build();
        }
      }
    }
    return getSendSharingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.CommandRequest,
      com.ada.federate.rpc.RPCService.CommandResponse> getCommandMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "command",
      requestType = com.ada.federate.rpc.RPCService.CommandRequest.class,
      responseType = com.ada.federate.rpc.RPCService.CommandResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.CommandRequest,
      com.ada.federate.rpc.RPCService.CommandResponse> getCommandMethod() {
    io.grpc.MethodDescriptor<com.ada.federate.rpc.RPCService.CommandRequest, com.ada.federate.rpc.RPCService.CommandResponse> getCommandMethod;
    if ((getCommandMethod = SegamGrpc.getCommandMethod) == null) {
      synchronized (SegamGrpc.class) {
        if ((getCommandMethod = SegamGrpc.getCommandMethod) == null) {
          SegamGrpc.getCommandMethod = getCommandMethod =
              io.grpc.MethodDescriptor.<com.ada.federate.rpc.RPCService.CommandRequest, com.ada.federate.rpc.RPCService.CommandResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "command"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCService.CommandRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ada.federate.rpc.RPCService.CommandResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SegamMethodDescriptorSupplier("command"))
              .build();
        }
      }
    }
    return getCommandMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SegamStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SegamStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SegamStub>() {
        @java.lang.Override
        public SegamStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SegamStub(channel, callOptions);
        }
      };
    return SegamStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SegamBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SegamBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SegamBlockingStub>() {
        @java.lang.Override
        public SegamBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SegamBlockingStub(channel, callOptions);
        }
      };
    return SegamBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SegamFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SegamFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SegamFutureStub>() {
        @java.lang.Override
        public SegamFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SegamFutureStub(channel, callOptions);
        }
      };
    return SegamFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SegamImplBase implements io.grpc.BindableService {

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
    public void rpcPing(com.ada.federate.rpc.RPCService.PingRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcPingMethod(), responseObserver);
    }

    /**
     */
    public void batchQuery(com.ada.federate.rpc.RPCCommon.SQLMessage request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getBatchQueryMethod(), responseObserver);
    }

    /**
     */
    public void pubicQuery(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.RPCResult> responseObserver) {
      asyncUnimplementedUnaryCall(getPubicQueryMethod(), responseObserver);
    }

    /**
     */
    public void privateQuery(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getPrivateQueryMethod(), responseObserver);
    }

    /**
     */
    public void privateSetUnion(com.ada.federate.rpc.RPCService.SetUnionRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCService.SetUnionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPrivateSetUnionMethod(), responseObserver);
    }

    /**
     */
    public void secureGroupByMax(com.ada.federate.rpc.RPCService.GroupByMaxRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getSecureGroupByMaxMethod(), responseObserver);
    }

    /**
     */
    public void secureGroupByMedian(com.ada.federate.rpc.RPCService.GroupByMedianRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getSecureGroupByMedianMethod(), responseObserver);
    }

    /**
     */
    public void secureGroupBySumSS(com.ada.federate.rpc.RPCService.GroupBySumSSRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.SSMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getSecureGroupBySumSSMethod(), responseObserver);
    }

    /**
     */
    public void sendSharing(com.ada.federate.rpc.RPCCommon.SSMessage request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getSendSharingMethod(), responseObserver);
    }

    /**
     */
    public void command(com.ada.federate.rpc.RPCService.CommandRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCService.CommandResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCommandMethod(), responseObserver);
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
            getRpcPingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCService.PingRequest,
                com.ada.federate.rpc.RPCCommon.Status>(
                  this, METHODID_RPC_PING)))
          .addMethod(
            getBatchQueryMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.SQLMessage,
                com.ada.federate.rpc.RPCCommon.Status>(
                  this, METHODID_BATCH_QUERY)))
          .addMethod(
            getPubicQueryMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.SQLExpression,
                com.ada.federate.rpc.RPCCommon.RPCResult>(
                  this, METHODID_PUBIC_QUERY)))
          .addMethod(
            getPrivateQueryMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.SQLExpression,
                com.ada.federate.rpc.RPCCommon.Status>(
                  this, METHODID_PRIVATE_QUERY)))
          .addMethod(
            getPrivateSetUnionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCService.SetUnionRequest,
                com.ada.federate.rpc.RPCService.SetUnionResponse>(
                  this, METHODID_PRIVATE_SET_UNION)))
          .addMethod(
            getSecureGroupByMaxMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCService.GroupByMaxRequest,
                com.ada.federate.rpc.RPCCommon.Status>(
                  this, METHODID_SECURE_GROUP_BY_MAX)))
          .addMethod(
            getSecureGroupByMedianMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCService.GroupByMedianRequest,
                com.ada.federate.rpc.RPCCommon.Status>(
                  this, METHODID_SECURE_GROUP_BY_MEDIAN)))
          .addMethod(
            getSecureGroupBySumSSMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCService.GroupBySumSSRequest,
                com.ada.federate.rpc.RPCCommon.SSMessage>(
                  this, METHODID_SECURE_GROUP_BY_SUM_SS)))
          .addMethod(
            getSendSharingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCCommon.SSMessage,
                com.ada.federate.rpc.RPCCommon.Status>(
                  this, METHODID_SEND_SHARING)))
          .addMethod(
            getCommandMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ada.federate.rpc.RPCService.CommandRequest,
                com.ada.federate.rpc.RPCService.CommandResponse>(
                  this, METHODID_COMMAND)))
          .build();
    }
  }

  /**
   */
  public static final class SegamStub extends io.grpc.stub.AbstractAsyncStub<SegamStub> {
    private SegamStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SegamStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SegamStub(channel, callOptions);
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
    public void rpcPing(com.ada.federate.rpc.RPCService.PingRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcPingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void batchQuery(com.ada.federate.rpc.RPCCommon.SQLMessage request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getBatchQueryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void pubicQuery(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.RPCResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPubicQueryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void privateQuery(com.ada.federate.rpc.RPCCommon.SQLExpression request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPrivateQueryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void privateSetUnion(com.ada.federate.rpc.RPCService.SetUnionRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCService.SetUnionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPrivateSetUnionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void secureGroupByMax(com.ada.federate.rpc.RPCService.GroupByMaxRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSecureGroupByMaxMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void secureGroupByMedian(com.ada.federate.rpc.RPCService.GroupByMedianRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSecureGroupByMedianMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void secureGroupBySumSS(com.ada.federate.rpc.RPCService.GroupBySumSSRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.SSMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSecureGroupBySumSSMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendSharing(com.ada.federate.rpc.RPCCommon.SSMessage request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendSharingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void command(com.ada.federate.rpc.RPCService.CommandRequest request,
        io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCService.CommandResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCommandMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SegamBlockingStub extends io.grpc.stub.AbstractBlockingStub<SegamBlockingStub> {
    private SegamBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SegamBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SegamBlockingStub(channel, callOptions);
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
    public com.ada.federate.rpc.RPCCommon.Status rpcPing(com.ada.federate.rpc.RPCService.PingRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcPingMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.Status batchQuery(com.ada.federate.rpc.RPCCommon.SQLMessage request) {
      return blockingUnaryCall(
          getChannel(), getBatchQueryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.RPCResult pubicQuery(com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return blockingUnaryCall(
          getChannel(), getPubicQueryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.Status privateQuery(com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return blockingUnaryCall(
          getChannel(), getPrivateQueryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCService.SetUnionResponse privateSetUnion(com.ada.federate.rpc.RPCService.SetUnionRequest request) {
      return blockingUnaryCall(
          getChannel(), getPrivateSetUnionMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.Status secureGroupByMax(com.ada.federate.rpc.RPCService.GroupByMaxRequest request) {
      return blockingUnaryCall(
          getChannel(), getSecureGroupByMaxMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.Status secureGroupByMedian(com.ada.federate.rpc.RPCService.GroupByMedianRequest request) {
      return blockingUnaryCall(
          getChannel(), getSecureGroupByMedianMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.SSMessage secureGroupBySumSS(com.ada.federate.rpc.RPCService.GroupBySumSSRequest request) {
      return blockingUnaryCall(
          getChannel(), getSecureGroupBySumSSMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCCommon.Status sendSharing(com.ada.federate.rpc.RPCCommon.SSMessage request) {
      return blockingUnaryCall(
          getChannel(), getSendSharingMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.ada.federate.rpc.RPCService.CommandResponse command(com.ada.federate.rpc.RPCService.CommandRequest request) {
      return blockingUnaryCall(
          getChannel(), getCommandMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SegamFutureStub extends io.grpc.stub.AbstractFutureStub<SegamFutureStub> {
    private SegamFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SegamFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SegamFutureStub(channel, callOptions);
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
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.Status> rpcPing(
        com.ada.federate.rpc.RPCService.PingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcPingMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.Status> batchQuery(
        com.ada.federate.rpc.RPCCommon.SQLMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getBatchQueryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.RPCResult> pubicQuery(
        com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return futureUnaryCall(
          getChannel().newCall(getPubicQueryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.Status> privateQuery(
        com.ada.federate.rpc.RPCCommon.SQLExpression request) {
      return futureUnaryCall(
          getChannel().newCall(getPrivateQueryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCService.SetUnionResponse> privateSetUnion(
        com.ada.federate.rpc.RPCService.SetUnionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPrivateSetUnionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.Status> secureGroupByMax(
        com.ada.federate.rpc.RPCService.GroupByMaxRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSecureGroupByMaxMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.Status> secureGroupByMedian(
        com.ada.federate.rpc.RPCService.GroupByMedianRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSecureGroupByMedianMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.SSMessage> secureGroupBySumSS(
        com.ada.federate.rpc.RPCService.GroupBySumSSRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSecureGroupBySumSSMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCCommon.Status> sendSharing(
        com.ada.federate.rpc.RPCCommon.SSMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getSendSharingMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ada.federate.rpc.RPCService.CommandResponse> command(
        com.ada.federate.rpc.RPCService.CommandRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCommandMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RPC_HELLO = 0;
  private static final int METHODID_RPC_PING = 1;
  private static final int METHODID_BATCH_QUERY = 2;
  private static final int METHODID_PUBIC_QUERY = 3;
  private static final int METHODID_PRIVATE_QUERY = 4;
  private static final int METHODID_PRIVATE_SET_UNION = 5;
  private static final int METHODID_SECURE_GROUP_BY_MAX = 6;
  private static final int METHODID_SECURE_GROUP_BY_MEDIAN = 7;
  private static final int METHODID_SECURE_GROUP_BY_SUM_SS = 8;
  private static final int METHODID_SEND_SHARING = 9;
  private static final int METHODID_COMMAND = 10;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SegamImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SegamImplBase serviceImpl, int methodId) {
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
        case METHODID_RPC_PING:
          serviceImpl.rpcPing((com.ada.federate.rpc.RPCService.PingRequest) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status>) responseObserver);
          break;
        case METHODID_BATCH_QUERY:
          serviceImpl.batchQuery((com.ada.federate.rpc.RPCCommon.SQLMessage) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status>) responseObserver);
          break;
        case METHODID_PUBIC_QUERY:
          serviceImpl.pubicQuery((com.ada.federate.rpc.RPCCommon.SQLExpression) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.RPCResult>) responseObserver);
          break;
        case METHODID_PRIVATE_QUERY:
          serviceImpl.privateQuery((com.ada.federate.rpc.RPCCommon.SQLExpression) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status>) responseObserver);
          break;
        case METHODID_PRIVATE_SET_UNION:
          serviceImpl.privateSetUnion((com.ada.federate.rpc.RPCService.SetUnionRequest) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCService.SetUnionResponse>) responseObserver);
          break;
        case METHODID_SECURE_GROUP_BY_MAX:
          serviceImpl.secureGroupByMax((com.ada.federate.rpc.RPCService.GroupByMaxRequest) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status>) responseObserver);
          break;
        case METHODID_SECURE_GROUP_BY_MEDIAN:
          serviceImpl.secureGroupByMedian((com.ada.federate.rpc.RPCService.GroupByMedianRequest) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status>) responseObserver);
          break;
        case METHODID_SECURE_GROUP_BY_SUM_SS:
          serviceImpl.secureGroupBySumSS((com.ada.federate.rpc.RPCService.GroupBySumSSRequest) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.SSMessage>) responseObserver);
          break;
        case METHODID_SEND_SHARING:
          serviceImpl.sendSharing((com.ada.federate.rpc.RPCCommon.SSMessage) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCCommon.Status>) responseObserver);
          break;
        case METHODID_COMMAND:
          serviceImpl.command((com.ada.federate.rpc.RPCService.CommandRequest) request,
              (io.grpc.stub.StreamObserver<com.ada.federate.rpc.RPCService.CommandResponse>) responseObserver);
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

  private static abstract class SegamBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SegamBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.ada.federate.rpc.RPCService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Segam");
    }
  }

  private static final class SegamFileDescriptorSupplier
      extends SegamBaseDescriptorSupplier {
    SegamFileDescriptorSupplier() {}
  }

  private static final class SegamMethodDescriptorSupplier
      extends SegamBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SegamMethodDescriptorSupplier(String methodName) {
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
      synchronized (SegamGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SegamFileDescriptorSupplier())
              .addMethod(getRpcHelloMethod())
              .addMethod(getRpcPingMethod())
              .addMethod(getBatchQueryMethod())
              .addMethod(getPubicQueryMethod())
              .addMethod(getPrivateQueryMethod())
              .addMethod(getPrivateSetUnionMethod())
              .addMethod(getSecureGroupByMaxMethod())
              .addMethod(getSecureGroupByMedianMethod())
              .addMethod(getSecureGroupBySumSSMethod())
              .addMethod(getSendSharingMethod())
              .addMethod(getCommandMethod())
              .build();
        }
      }
    }
    return result;
  }
}
