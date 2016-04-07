#!/usr/bin/bash

# adjust the following property:
SERVERS_DIR=/c/DEV/bin/jms-cluster-test/jboss-eap-6.4

MODULE_DIR=modules/system/layers/base/org/jboss/genericjms/provider/main
for server in live1 live2 backup1 backup2
do
   server_dir="$SERVERS_DIR/$server"
   absolute_module_dir="$server_dir/$MODULE_DIR"
   echo "Installing $absolute_module_dir"
   mkdir -p $absolute_module_dir
   cat << EOF > $absolute_module_dir/module.xml
<module xmlns="urn:jboss:module:1.1" name="org.jboss.genericjms.provider">
	<resources>
		<!-- all jars required by the JMS provider, in this case Tibco -->
		<resource-root path="tibjms.jar"/>
		<resource-root path="tibcrypt.jar"/>
	</resources>
	<dependencies>
		<module name="javax.api"/>
		<module name="javax.jms.api"/>
	</dependencies>
</module>
EOF
    #cp tibjms.jar tibcrypt.jar $absolute_module_dir/
    echo "TODO: copy manually tibjms.jar tibcrypt.jar to $absolute_module_dir/"
    cp "$server/standalone/configuration/standalone-full-ha.xml" "$SERVERS_DIR/$server/standalone/configuration/"
done
