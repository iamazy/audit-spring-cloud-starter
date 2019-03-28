package com.iamazy.springcloud.audit.cons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
public interface CoreConstants {

    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    ObjectMapper OBJECT_MAPPER=new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    char COLON_CHAR = ':';

    String SEMI_COLON = ";";

    char COMMA_CHAR = ',';

    String COMMA = ",";

    char EQ_CHAR = '=';

    char DASH_CHAR = '-';

    char OPEN_BRACES_CHAR = '{';

    char CLOSE_BRACES_CHAR = '}';

    String BRACKETS = "[]";

    char OPEN_BRACKETS_CHAR = '[';

    char CLOSE_BRACKETS_CHAR = ']';

    String ARROW = "==>";

    char PIPE = '|';

    char SPACE = ' ';

    char WINDOWS_SEPARATOR = '\\';

    char UNIX_SEPARATOR = '/';

    String READ_WRITE = "rw";

    String NEW_LINE = "\n";

    char DOLLAR_CHAR = '$';

    String NULL = "null";

    String EMPTY = "<empty>";

    String ANONYMOUS="anonymous";


    /**
     * Success:1 Failure:0
     */
    Integer SUCCESS=1;
    Integer FAILURE=0;

}
