/**
 * Copyright 2014 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.analytics.server;

import com.arcbees.analytics.server.options.ServerOptionsCallback;
import com.arcbees.analytics.shared.Analytics;
import com.arcbees.analytics.shared.GaAccount;
import com.google.inject.Singleton;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;

public class ServerAnalyticsModule extends ServletModule {
    private final String userAccount;

    public ServerAnalyticsModule(final String userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    protected void configureServlets() {
        bindConstant().annotatedWith(GaAccount.class).to(userAccount);
        bind(ServerOptionsCallback.class).toProvider(ServerOptionsCallbackProvider.class).in(RequestScoped.class);
        bind(Analytics.class).to(ServerAnalytics.class).in(Singleton.class);
        filter("/*").through(ServerOptionsCallbackProvider.class);
    }
}
