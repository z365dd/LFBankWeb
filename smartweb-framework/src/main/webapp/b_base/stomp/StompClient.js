(function() {
  var StompClient, StompClientBuilder;

  StompClient = (function() {
    function StompClient(params) {
      this.props = {
        onConnect: () => {},
        onDisconnect: () => {},
        getRetryInterval: count => {
          return 1000 * count;
        },
        options: {},
        headers: {},
        autoReconnect: true,
        debug: false,
        heartbeat: 10000
      };
      params = params || {};
      this.props = $.extend(this.props, params);
      this.connected = false;
      this.explicitDisconnect = false;
      this.subscriptions = new Map();
      this.retryCount = 0;
    }

    /**
     * 初始化stomp客户端
     */
    StompClient.prototype._initStompClient = function() {
      const {
        url,
        heartbeat,
        heartbeatIncoming,
        heartbeatOutgoing,
        debug
      } = this.props;
      this.client = Stomp.over(new WebSocket(url));

      this.client.heartbeat.outgoing = heartbeat;
      this.client.heartbeat.incoming = heartbeat;

      if (Object.keys(this.props).includes("heartbeatIncoming")) {
        this.client.heartbeat.incoming = heartbeatIncoming;
      }
      if (Object.keys(this.props).includes("heartbeatOutgoing")) {
        this.client.heartbeat.outgoing = heartbeatOutgoing;
      }
      if (!debug) {
        this.client.debug = () => {};
      }
    };

    StompClient.prototype._processMessage = function(msgBody) {
      try {
        return JSON.parse(msgBody);
      } catch (e) {
        return msgBody;
      }
    };
    /**
     * 订阅
     */
    StompClient.prototype._subscribe = function(topic) {
      const { onMessage, subscribeHeaders } = this.props;
      if (!this.subscriptions.has(topic)) {
        const sub = this.client.subscribe(
          topic,
          msg =>
            onMessage(this._processMessage(msg.body), msg.headers.destination),
          subscribeHeaders
        );
        this.subscriptions.set(topic, sub);
      }
    };
    StompClient.prototype._cleanUp = function() {
      this.connected = false;
      this.retryCount = 0;
      this.subscriptions.clear();
    };

    StompClient.prototype._log = function(msg) {
      const { debug } = this.props;
      if (debug) {
        console.log(msg);
      }
    };
    /**
     * 连接
     */
    StompClient.prototype._connect = function() {
      const {
        headers,
        topics,
        onConnect,
        onConnectFailure,
        onDisconnect,
        getRetryInterval,
        autoReconnect
      } = this.props;
      this._initStompClient();
      this.client.connect(
        headers,
        () => {
          this.connected = true;
          topics.forEach(topic => {
            console.log("订阅主题[" + topic + "]");
            this._subscribe(topic);
          });
          onConnect();
        },
        error => {
          if (error) {
            if (Object.keys(this.props).includes("onConnectFailure")) {
              onConnectFailure(error);
            } else {
              this._log(error.stack);
            }
          }
          if (this.connected) {
            this._cleanUp();
            onDisconnect();
          }
          if (autoReconnect && !this.explicitDisconnect) {
        	this.subscriptions = new Map();
            this.disconnect();
            this.retryCount += 1;
            this._timeoutId = setTimeout(
              (function(_this) {
                return function() {
                  _this.connect();
                  return typeof _this.debug === "function"
                    ? _this.debug(">>> RECONNECT")
                    : void 0;
                };
              })(this),
              getRetryInterval(this.retryCount)
            );
          }
        }
      );
    };

    StompClient.prototype.connect = function() {
      this.explicitDisconnect = false;
      if (!this.connected) {
        this._connect();
      }
    };

    StompClient.prototype._unsubscribe = function(topic) {
      const sub = this.subscriptions.get(topic);
      sub.unsubscribe();
      this.subscriptions.delete(topic);
    };

    /**
     * 断开STOMP客户端并禁用所有重新连接。
     *
     * @public
     */
    StompClient.prototype.disconnect = function() {
      const { onDisconnect } = this.props;
      if (this._timeoutId) {
        clearTimeout(this._timeoutId);
        this._timeoutId = null;
      }
      this.explicitDisconnect = true;
      if (this.connected) {
        this.subscriptions.forEach((subid, topic) => {
          this._unsubscribe(topic);
        });
        this.client.disconnect(() => {
          this._cleanUp();
          onDisconnect();
          this._log("Stomp client is successfully disconnected!");
        });
      }
    };

    /**
     * Send message to the specified topic.
     *
     * @param {string} topic target topic to send message
     * @param {string} msg message to send
     * @param {Object} [opt_headers={}] additional headers for underlying STOMP client
     * @public
     */
    StompClient.prototype.sendMessage = function(topic, msg, opt_headers = {}) {
      if (this.connected) {
        this.client.send(topic, opt_headers, msg);
      } else {
        throw new Error("Send error: SockJsClient is disconnected");
      }
    };
    return StompClient;
  })();

  StompClientBuilder = {
    init: function(props) {
      var stompClient = new StompClient(props);
      stompClient.connect();
      return stompClient;
    }
  };

  if (typeof exports !== "undefined" && exports !== null) {
    exports.StompClientBuilder = StompClientBuilder;
  }
  if (typeof window !== "undefined" && window !== null) {
    window.StompClientBuilder = StompClientBuilder;
  } else if (!exports) {
    self.StompClientBuilder = StompClientBuilder;
  }
}.call(this));
