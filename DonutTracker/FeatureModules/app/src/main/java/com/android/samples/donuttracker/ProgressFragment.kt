/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.samples.donuttracker

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.dynamicfeatures.fragment.ui.AbstractProgressFragment

class ProgressFragment : AbstractProgressFragment(R.layout.fragment_progress) {

    private var message: TextView? = null
    private var progressBar: ProgressBar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            message = findViewById(R.id.message)
            progressBar = findViewById(R.id.progressBar)
        }
    }

    override fun onProgress(status: Int, bytesDownloaded: Long, bytesTotal: Long) {
        progressBar?.progress = (bytesDownloaded.toDouble() * 100 / bytesTotal).toInt()
    }

    override fun onFailed(errorCode: Int) {
        message?.text = getString(R.string.install_failed)
    }

    override fun onCancelled() {
        message?.text = getString(R.string.install_cancelled)
    }
}
