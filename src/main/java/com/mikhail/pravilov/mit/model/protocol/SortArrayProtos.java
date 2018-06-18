// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SortArrayRequest.proto

package com.mikhail.pravilov.mit.model.protocol;

public final class SortArrayProtos {
    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_testConfiguration_SortArray_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_testConfiguration_SortArray_fieldAccessorTable;
    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        java.lang.String[] descriptorData = {
                "\n\026SortArrayRequest.proto\022\021testConfigurat" +
                        "ion\"\033\n\tSortArray\022\016\n\006number\030\001 \003(\003B;\n(com." +
                        "mikhail.pravilov.mit.model.statisticB\017So" +
                        "rtArrayProtos"
        };
        com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    public com.google.protobuf.ExtensionRegistry assignDescriptors(
                            com.google.protobuf.Descriptors.FileDescriptor root) {
                        descriptor = root;
                        return null;
                    }
                };
        com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                        }, assigner);
        internal_static_testConfiguration_SortArray_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_testConfiguration_SortArray_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_testConfiguration_SortArray_descriptor,
                new java.lang.String[]{"Number",});
    }

    private SortArrayProtos() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    public interface SortArrayOrBuilder extends
            // @@protoc_insertion_point(interface_extends:testConfiguration.SortArray)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>repeated int64 number = 1;</code>
         */
        java.util.List<java.lang.Long> getNumberList();

        /**
         * <code>repeated int64 number = 1;</code>
         */
        int getNumberCount();

        /**
         * <code>repeated int64 number = 1;</code>
         */
        long getNumber(int index);
    }

    /**
     * Protobuf type {@code testConfiguration.SortArray}
     */
    public static final class SortArray extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:testConfiguration.SortArray)
            SortArrayOrBuilder {
        public static final int NUMBER_FIELD_NUMBER = 1;
        @java.lang.Deprecated
        public static final com.google.protobuf.Parser<SortArray>
                PARSER = new com.google.protobuf.AbstractParser<SortArray>() {
            public SortArray parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new SortArray(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0L;
        // @@protoc_insertion_point(class_scope:testConfiguration.SortArray)
        private static final SortArrayProtos.SortArray DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new SortArrayProtos.SortArray();
        }

        private java.util.List<java.lang.Long> number_;
        private byte memoizedIsInitialized = -1;

        // Use SortArray.newBuilder() to construct.
        private SortArray(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SortArray() {
            number_ = java.util.Collections.emptyList();
        }

        private SortArray(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new java.lang.NullPointerException();
            }
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 8: {
                            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                                number_ = new java.util.ArrayList<java.lang.Long>();
                                mutable_bitField0_ |= 0x00000001;
                            }
                            number_.add(input.readInt64());
                            break;
                        }
                        case 10: {
                            int length = input.readRawVarint32();
                            int limit = input.pushLimit(length);
                            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001) && input.getBytesUntilLimit() > 0) {
                                number_ = new java.util.ArrayList<java.lang.Long>();
                                mutable_bitField0_ |= 0x00000001;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                number_.add(input.readInt64());
                            }
                            input.popLimit(limit);
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                    number_ = java.util.Collections.unmodifiableList(number_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return SortArrayProtos.internal_static_testConfiguration_SortArray_descriptor;
        }

        public static SortArrayProtos.SortArray parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SortArrayProtos.SortArray parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SortArrayProtos.SortArray parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SortArrayProtos.SortArray parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SortArrayProtos.SortArray parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SortArrayProtos.SortArray parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SortArrayProtos.SortArray parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static SortArrayProtos.SortArray parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SortArrayProtos.SortArray parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static SortArrayProtos.SortArray parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SortArrayProtos.SortArray parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static SortArrayProtos.SortArray parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SortArrayProtos.SortArray prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public static SortArrayProtos.SortArray getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static com.google.protobuf.Parser<SortArray> parser() {
            return PARSER;
        }

        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return SortArrayProtos.internal_static_testConfiguration_SortArray_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            SortArrayProtos.SortArray.class, SortArrayProtos.SortArray.Builder.class);
        }

        /**
         * <code>repeated int64 number = 1;</code>
         */
        public java.util.List<java.lang.Long>
        getNumberList() {
            return number_;
        }

        /**
         * <code>repeated int64 number = 1;</code>
         */
        public int getNumberCount() {
            return number_.size();
        }

        /**
         * <code>repeated int64 number = 1;</code>
         */
        public long getNumber(int index) {
            return number_.get(index);
        }

        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            for (int i = 0; i < number_.size(); i++) {
                output.writeInt64(1, number_.get(i));
            }
            unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            {
                int dataSize = 0;
                for (int i = 0; i < number_.size(); i++) {
                    dataSize += com.google.protobuf.CodedOutputStream
                            .computeInt64SizeNoTag(number_.get(i));
                }
                size += dataSize;
                size += 1 * getNumberList().size();
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SortArrayProtos.SortArray)) {
                return super.equals(obj);
            }
            SortArrayProtos.SortArray other = (SortArrayProtos.SortArray) obj;

            boolean result = true;
            result = result && getNumberList()
                    .equals(other.getNumberList());
            result = result && unknownFields.equals(other.unknownFields);
            return result;
        }

        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            if (getNumberCount() > 0) {
                hash = (37 * hash) + NUMBER_FIELD_NUMBER;
                hash = (53 * hash) + getNumberList().hashCode();
            }
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        @java.lang.Override
        public com.google.protobuf.Parser<SortArray> getParserForType() {
            return PARSER;
        }

        public SortArrayProtos.SortArray getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        /**
         * Protobuf type {@code testConfiguration.SortArray}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:testConfiguration.SortArray)
                SortArrayProtos.SortArrayOrBuilder {
            private int bitField0_;
            private java.util.List<java.lang.Long> number_ = java.util.Collections.emptyList();

            // Construct using com.mikhail.pravilov.mit.model.protocol.SortArrayProtos.SortArray.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return SortArrayProtos.internal_static_testConfiguration_SortArray_descriptor;
            }

            protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return SortArrayProtos.internal_static_testConfiguration_SortArray_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                SortArrayProtos.SortArray.class, SortArrayProtos.SortArray.Builder.class);
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                number_ = java.util.Collections.emptyList();
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return SortArrayProtos.internal_static_testConfiguration_SortArray_descriptor;
            }

            public SortArrayProtos.SortArray getDefaultInstanceForType() {
                return SortArrayProtos.SortArray.getDefaultInstance();
            }

            public SortArrayProtos.SortArray build() {
                SortArrayProtos.SortArray result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public SortArrayProtos.SortArray buildPartial() {
                SortArrayProtos.SortArray result = new SortArrayProtos.SortArray(this);
                int from_bitField0_ = bitField0_;
                if (((bitField0_ & 0x00000001) == 0x00000001)) {
                    number_ = java.util.Collections.unmodifiableList(number_);
                    bitField0_ = (bitField0_ & ~0x00000001);
                }
                result.number_ = number_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return super.clone();
            }

            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    java.lang.Object value) {
                return super.setField(field, value);
            }

            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return super.clearField(field);
            }

            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return super.clearOneof(oneof);
            }

            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, java.lang.Object value) {
                return super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    java.lang.Object value) {
                return super.addRepeatedField(field, value);
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof SortArrayProtos.SortArray) {
                    return mergeFrom((SortArrayProtos.SortArray) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(SortArrayProtos.SortArray other) {
                if (other == SortArrayProtos.SortArray.getDefaultInstance()) return this;
                if (!other.number_.isEmpty()) {
                    if (number_.isEmpty()) {
                        number_ = other.number_;
                        bitField0_ = (bitField0_ & ~0x00000001);
                    } else {
                        ensureNumberIsMutable();
                        number_.addAll(other.number_);
                    }
                    onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                SortArrayProtos.SortArray parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (SortArrayProtos.SortArray) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureNumberIsMutable() {
                if (!((bitField0_ & 0x00000001) == 0x00000001)) {
                    number_ = new java.util.ArrayList<java.lang.Long>(number_);
                    bitField0_ |= 0x00000001;
                }
            }

            /**
             * <code>repeated int64 number = 1;</code>
             */
            public java.util.List<java.lang.Long>
            getNumberList() {
                return java.util.Collections.unmodifiableList(number_);
            }

            /**
             * <code>repeated int64 number = 1;</code>
             */
            public int getNumberCount() {
                return number_.size();
            }

            /**
             * <code>repeated int64 number = 1;</code>
             */
            public long getNumber(int index) {
                return number_.get(index);
            }

            /**
             * <code>repeated int64 number = 1;</code>
             */
            public Builder setNumber(
                    int index, long value) {
                ensureNumberIsMutable();
                number_.set(index, value);
                onChanged();
                return this;
            }

            /**
             * <code>repeated int64 number = 1;</code>
             */
            public Builder addNumber(long value) {
                ensureNumberIsMutable();
                number_.add(value);
                onChanged();
                return this;
            }

            /**
             * <code>repeated int64 number = 1;</code>
             */
            public Builder addAllNumber(
                    java.lang.Iterable<? extends java.lang.Long> values) {
                ensureNumberIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(
                        values, number_);
                onChanged();
                return this;
            }

            /**
             * <code>repeated int64 number = 1;</code>
             */
            public Builder clearNumber() {
                number_ = java.util.Collections.emptyList();
                bitField0_ = (bitField0_ & ~0x00000001);
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:testConfiguration.SortArray)
        }

    }

    // @@protoc_insertion_point(outer_class_scope)
}
