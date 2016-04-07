Tibco EMS HowTo
=========================================================

This document describes how to set up Tibco EMS server used in this example project.

Installation
---------------
Download and unpack Tibco EMS zip archive.
This examples in this project has been tested with Tibco EMS version 7.0.


Configuration
------------------
Edit `bin/factories.conf`:

Add an XA factory:

    # XAQCF is queue factory used in this example
    [XAQCF]
      type                  = xaqueue
      url                   = tcp://localhost:7222

    [QueueConnectionFactory]
      type                     = queue
      url                      = tcp://localhost:7222
      multicast_enabled        = false

    [TopicConnectionFactory]
      type                     = topic
      url                      = tcp://localhost:7222
      multicast_enabled        = false
