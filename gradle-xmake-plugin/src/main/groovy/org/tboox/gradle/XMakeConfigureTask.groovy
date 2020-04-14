/*!A gradle plugin that integrates xmake seamlessly
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2020-present, TBOOX Open Source Group.
 *
 * @author      ruki
 * @file        XMakeConfigureTask.groovy
 *
 */
package org.tboox.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.TaskAction

class XMakeConfigureTask extends DefaultTask {

    // tag
    private final String TAG = "[xmake]: "

    // the logger
    private final Logger logger = Logging.getLogger("xmake")

    // the plugin extension
    XMakePluginExtension extension

    XMakeConfigureTask() {
        setGroup("xmake")
        setDescription("Configure a Build with XMake")
    }

    // build command line
    private List<String> buildCmdLine() {
        List<String> parameters = new ArrayList<>();
        parameters.add("xmake");
        parameters.add("f");
        parameters.add("-y");
        parameters.add("-c");
        parameters.add("-p");
        parameters.add("android");
        return parameters;
    }

    @TaskAction
    void configure() {

        // trace
        logger.warn(TAG + "do configure")

        // check
        if (!new File(extension.path).isFile()) {
            throw new GradleException(TAG + extension.path + " not found!")
        }

        // do configure
        XMakeExecutor executor = new XMakeExecutor()
        executor.exec(buildCmdLine(), new File(extension.path).parentFile.absoluteFile)
    }
}