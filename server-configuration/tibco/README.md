Tibco EMS configuration
=========================================================

Installation
---------------
Download and unpack Tibco EMS zip archive


Configuration
------------------
Edit `bin/factories.conf`:

Add XA factory:
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




