/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013-2014 Felix Schulze
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.felixschulze.gradle.util

import org.gradle.api.Nullable

import java.util.regex.Pattern

class FileHelper {

    @Nullable
    def static getFile(String regex, File directory) {
        def files = getFiles(regex, directory)
        if (files) {
            return files[0]
        } else {
            return null
        }
    }

    def static getFiles(String regex, File directory) {
        if (!regex || !directory || !directory.exists()) {
            return []
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("$directory.absolutePath is not a directory")
        }

        def pattern = Pattern.compile(regex)

        def fileList = directory.list(
                [accept: { d, f -> f ==~ pattern }] as FilenameFilter
        ).toList()

        if (!fileList) {
            return []
        }
        return fileList.collect {
            new File(directory, it)
        }
    }
}
