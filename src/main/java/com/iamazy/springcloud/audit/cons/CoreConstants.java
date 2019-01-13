package com.iamazy.springcloud.audit.cons;

/**
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
public interface CoreConstants {

    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

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
