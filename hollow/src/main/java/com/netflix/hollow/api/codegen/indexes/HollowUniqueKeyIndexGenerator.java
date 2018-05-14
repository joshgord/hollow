/*
 *
 *  Copyright 2017 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package com.netflix.hollow.api.codegen.indexes;

import com.netflix.hollow.api.codegen.CodeGeneratorConfig;
import com.netflix.hollow.api.codegen.HollowAPIGenerator;
import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.index.AbstractHollowUniqueKeyIndex;
import com.netflix.hollow.api.custom.HollowAPI;
import com.netflix.hollow.core.HollowDataset;
import com.netflix.hollow.core.schema.HollowObjectSchema;
import com.netflix.hollow.core.schema.HollowSchema;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class contains template logic for generating a {@link HollowAPI} implementation.  Not intended for external consumption.
 *
 * @see HollowAPIGenerator
 */
public class HollowUniqueKeyIndexGenerator extends HollowIndexGenerator {

    protected final HollowObjectSchema schema;
    protected final String type;

    protected boolean isGenSimpleConstructor = false;
    protected boolean isParameterizedConstructorPublic = true;
    protected boolean isAutoListenToDataRefresh = false;

    public HollowUniqueKeyIndexGenerator(String packageName, String apiClassname, HollowObjectSchema schema,
            HollowDataset dataset, CodeGeneratorConfig config) {
        super(packageName, apiClassname, dataset, config);

        this.type = schema.getName();
        this.className = getClassName(schema);
        this.schema = schema;
    }

    protected String getClassName(HollowObjectSchema schema) {
        return schema.getName() + "UniqueKeyIndex";
    }

    @Override
    public String generate() {
        StringBuilder builder = new StringBuilder();
        appendPackageAndCommonImports(builder, apiClassname,
            Collections.<HollowSchema>singletonList(schema));
        builder.append("import ").append(HollowConsumer.class.getName()).append(";\n");
        builder.append("import ").append(AbstractHollowUniqueKeyIndex.class.getName())
            .append(";\n");
        if (isGenSimpleConstructor)
            builder.append("import ").append(HollowObjectSchema.class.getName()).append(";\n");

        builder.append("\n@SuppressWarnings(\"all\")\n");
        builder.append("public class ").append(className).append(" extends ")
            .append(AbstractHollowUniqueKeyIndex.class.getSimpleName()).append("<")
            .append(apiClassname).append(", ").append(hollowImplClassname(type)).append("> {\n\n");
        {
            genConstructors(builder);
            genPublicAPIs(builder);
        }

        builder.append("}");

        return builder.toString();
    }

    protected void genConstructors(StringBuilder builder) {
        if (isGenSimpleConstructor)
            genSimpleConstructor(builder);

        genParameterizedConstructor(builder);
    }

    protected void genSimpleConstructor(StringBuilder builder) {
        builder.append("    public ").append(className).append("(HollowConsumer consumer) {\n");
        builder.append("        this(consumer, ").append(isAutoListenToDataRefresh).append(");");
        builder.append("    }\n\n");

        builder.append("    public ").append(className)
            .append("(HollowConsumer consumer, boolean isListenToDataRefresh) {\n");
        builder.append(
            "        this(consumer, isListenToDataRefresh, ((HollowObjectSchema)consumer.getStateEngine().getNonNullSchema(\"")
            .append(type).append("\")).getPrimaryKey().getFieldPaths());\n");
        builder.append("    }\n\n");

    }

    protected void genParameterizedConstructor(StringBuilder builder) {
        builder.append("    ").append(isParameterizedConstructorPublic ? "public " : "private ")
            .append(className).append("(HollowConsumer consumer, String... fieldPaths) {\n");
        builder.append("        this(consumer, ").append(isAutoListenToDataRefresh)
            .append(", fieldPaths);\n");
        builder.append("    }\n\n");

        builder.append("    ").append(isParameterizedConstructorPublic ? "public " : "private ")
            .append(className).append(
            "(HollowConsumer consumer, boolean isListenToDataRefresh, String... fieldPaths) {\n");
        builder.append("        super(consumer, \"").append(type)
            .append("\", isListenToDataRefresh, fieldPaths);\n");
        builder.append("    }\n\n");

    }

    protected void genPublicAPIs(StringBuilder builder) {
        genFindMatchAPI(builder);
    }

    protected void genFindMatchAPI(StringBuilder builder) {
        builder.append("    public ").append(hollowImplClassname(type))
            .append(" findMatch(Object... keys) {\n");
        builder.append("        int ordinal = idx.getMatchingOrdinal(keys);\n");
        builder.append("        if(ordinal == -1)\n");
        builder.append("            return null;\n");
        builder.append("        return api.get").append(hollowImplClassname(type))
            .append("(ordinal);\n");
        builder.append("    }\n\n");
    }
}
