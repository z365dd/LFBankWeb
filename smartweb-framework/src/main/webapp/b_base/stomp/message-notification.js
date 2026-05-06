(function () {
    let StompC;
    StompC = (function () {
        // 设置 STOMP 客户端
        let client
        // 设置 WebSocket 进入端点
        const SOCKET_ENDPOINT = `${ctx}/ws`;
        // 设置订阅消息的请求地址前缀
        const SUBSCRIBE_PREFIX = "/sys/topic";
        // 设置订阅地址
        const SUBSCRIBE = "";
        // 设置服务器端点，访问服务器中哪个接口
        const SEND_ENDPOINT = "/app/test";

        function StompC() {
        }

        /**
         * 进行连接
         */
        StompC.prototype.connect = (subscriptions, headers) => {
            if (headers === undefined) {
                headers = {};
            }
            // 设置 SOCKET
            const socket = new SockJS(SOCKET_ENDPOINT);
            // 配置 STOMP 客户端
            client = Stomp.over(socket);
            // 关闭调试
            client.debug = () => {
            };
            // STOMP 客户端连接
            client.connect(headers, frame => {
                console.debug("连接成功");
                if (!subscriptions) {
                    return;
                }
                for (const subscription of subscriptions) {
                    if (typeof subscription === "object") {
                        const { destination, onMessage, headers } = subscription;
                        subscribe(destination, onMessage, headers);
                    }
                }
            });
        }

        /**
         * 订阅消息
         * @param destination 消息端点
         * @param onMessage 消息回调
         * @param headers 请求头
         */
        const subscribe = (destination, onMessage, headers) => {
            // 设置订阅地址
            const subscribe = SUBSCRIBE_PREFIX + destination;
            const userSubscribe = "/user" + subscribe;
            // 输出订阅地址
            console.debug("设置订阅地址为：" + subscribe);
            const callback = responseBody => {
                const receiveMessage = JSON.parse(responseBody.body);
                console.debug(receiveMessage);
                if (typeof onMessage === "function") {
                    onMessage(receiveMessage);
                }
            }
            // 执行订阅消息
            client.subscribe(subscribe, callback, headers);
            client.subscribe(userSubscribe, callback, headers);
        }

        return StompC;
    })();
    if (typeof exports !== "undefined" && exports !== null) {
        exports.StompC = StompC;
    }
    if (typeof window !== "undefined" && window !== null) {
        window.StompC = StompC;
    } else if (!exports) {
        self.StompC = StompC;
    }
}).call(this);

const stompC = new StompC();;

const showInfo = (title, content, id, timeout) => {
    const buttons = [];
    if (id) {
        buttons.push({
            text: '详情',
            click: function (e) {
                $.session.set('id', id);
                parent.window.openMenu("平台管理->系统设置->公告消息");
            }
        });
    }
    buttons.push({
        text: '关闭',
        click: function (e) {
            e.closeNotification()
        }
    });
    notification().info({
        title: title,
        text: content,
        timeout: timeout ? timeout : 30 * 1000,
        buttons: buttons
    })
};