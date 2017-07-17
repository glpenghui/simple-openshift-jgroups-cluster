/**
 *  Copyright 2005-2015 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.everythingjboss.jgroups;

import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import java.io.*;
import java.util.List;
import java.util.LinkedList;

public class Main extends ReceiverAdapter {
    JChannel channel;
    String user_name = System.getProperty("user.name", "n/a");
    final List<String> state = new LinkedList<String>();

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    private void start() throws Exception {
        ClassLoader cl = Main.class.getClassLoader();
        InputStream is = cl.getResourceAsStream("mytcpgossip.xml");

        // The same system properties could be set thru the startup scripts of
        // any container running JGroups 
        System.setProperty("GOSSIP_ROUTER_SERVICE_HOST", System.getenv("GOSSIP_ROUTER_SERVICE_HOST"));
        System.setProperty("java.net.preferIPv4Stack", "true");

        channel = new JChannel(is);
        channel.setReceiver(this);
        channel.connect("ChatCluster");
    }

    public static void main(String[] args) throws Exception {
        new Main().start();
    }
}
