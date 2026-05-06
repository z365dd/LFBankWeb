// bip sound
// http://soundbible.com/mp3/A-Tone-His_Self-1266414414.mp3

(function (factory) {

    // checking for exports avalible
    if (typeof module !== 'undefined' && module.exports) {
        // export Collection
        module.exports = factory
    } else {
        // else add to root variable
        window['notification'] = factory
    }

})(function () {

    function setSideUpAnimation(finalNotification) {
        setTimeout(function () {

            const notificationHeight = finalNotification
                .querySelector('.message-notification-body')
                .offsetHeight

            finalNotification.style.height = notificationHeight + 'px'
        }, 0)
    }

    function createText(text) {
        return document.createTextNode(text)
    }

    /**
     * provide a reusable way to create html
     * elements
     * @param tag String – html tag name
     * @param classes Array<String> – html tag classes
     */

    function createElement(tag, classes) {
        const $HTMLElement = document.createElement(tag)
        if (!!classes) {
            classes.forEach(function (className) {
                $HTMLElement.classList.add(className)
            })
        }

        return $HTMLElement
    }

    let $messageBox = document.querySelector('.message-notification-box')

    let $newNotificationsAdvice;

    if (!$messageBox) {
        $messageBox = createElement('div', ['message-notification-box'])
        $newNotificationsAdvice = createElement('div', ['message-notification-advice'])
        $newNotificationsAdvice.addEventListener('click', function () {
            $messageBox.scrollTop = 0;
        })
        $messageBox.appendChild($newNotificationsAdvice)

        document.body.appendChild($messageBox)
    }

    $messageBox.__proto__.unshifElement = function (node) {
        this.insertBefore(node, this.childNodes[0])
    }

    $messageBox.addEventListener('scroll', function (e) {
        if (e.currentTarget.scrollTop < 20) {
            $newNotificationsAdvice.classList.remove('active')
        }
    })

    return {
        info: function (argm) {
            this.createNotification('info', argm)
        },

        /*
        * Internal methods for
        * launch notifications
        */
        createNotification: function (type, argm) {

            this.type = type
            this.title = argm.title
            this.text = argm.text
            this.icon = (argm.icon === undefined) ? true : argm.icon
            this.buttons = argm.buttons

            const $notification = this.$createContainer()
            const $body = $notification.querySelector('div')

            this.$notification = $notification
            this.$body = $body

            const $title = this.createTitle()
            const $text = this.createText()

            const $messageContent = createElement('div', [
                'message-content'
            ])

            $messageContent.appendChild($text)

            $body.appendChild($title)

            $body.appendChild($messageContent)

            // render buttons fragment if exists
            if (this.buttons) {
                const $buttons = this.createButtons($notification, $body)

                $body
                    .querySelector('.message-content')
                    .appendChild($buttons)
            }


            const $close = createElement('div', [
                'message-notification-close-icon'
            ])

            $close.addEventListener('click', (function () {
                this.closeNotification()
            }).bind(this))

            // var $close = document.createElement('div')
            // $close.classList.add('message-notification-close-icon')
            $close.innerHTML = this.chooseIcon.close

            $body.appendChild($close)

            $messageBox.unshifElement($notification)
            setSideUpAnimation($notification)

            if ($messageBox.scrollTop > 20) {
                $newNotificationsAdvice.classList.add('active')
                $newNotificationsAdvice.innerHTML = this.chooseIcon.newNotification
            }

            if (argm.timeout !== '-1') {
                setTimeout(
                    (function () {
                        this.closeNotification()
                    }).bind(this),
                    argm.timeout || 5000
                )
            }
        },
        $createContainer: function () {
            // generate box for notification

            const $container = createElement('div', [
                'message-notification',
            ])

            const $innerContainer = createElement('div', [
                'message-notification-body',
                'message-' + this.type
            ])

            $container.appendChild($innerContainer)

            return $container
        },
        createTitle: function () {
            const $paragraph = createElement('h3')
            const tti = createText(this.title)
            $paragraph.appendChild(tti)

            return $paragraph
        },
        createText: function () {
            const $title = createElement('p', [
                'message-paragraph'
            ])

            const $tx = document.createTextNode(this.text)
            $title.appendChild($tx)

            return $title
        },
        createButtons: function ($notification, $body) {
            const $buttonsContainer = createElement('div', [
                'message-buttons-container'
            ])

            const self = this

            this.buttons.forEach(function (button) {
                const $buttonElement = createElement('button')
                $buttonElement.appendChild(document.createTextNode(button.text))

                $buttonElement.addEventListener('click', function (event) {

                    self.removeNotification = true
                    event.preventClose = function () {
                        self.removeNotification = false
                    }

                    event.closeNotification = function () {
                        self.closeNotification()
                    }

                    button.click(event)

                    if (self.removeNotification) self.closeNotification()
                })

                $buttonsContainer.appendChild($buttonElement)
            })

            return $buttonsContainer
        },
        closeNotification: function () {
            const self = this
            if (!this.elementWasRemoved) {
                self.$body.style.opacity = '0'
                setTimeout(function () {
                    self.$body.style.marginTop = '0px'
                    self.$body.style.marginBottom = '0px'
                    self.$body.style.padding = '0px'
                    self.$notification.style.height = 0 + 'px'
                    self.$notification.style.padding = 0 + 'px'
                    setTimeout(function () {
                        self.$notification
                            .parentNode
                            .removeChild(
                                self.$notification
                            )
                    }, 600);
                    if ($messageBox.scrollTop < 20) {
                        if ($newNotificationsAdvice) {
                            $newNotificationsAdvice.classList.remove('active')
                        }
                    }
                }, 150)
            }
            this.elementWasRemoved = true
        },
        chooseIcon: {
            info: '<svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><circle cx="24" cy="24" r="24" fill="#EEEEEE"/><path d="M26.595 37.5C26.3313 37.9546 25.9528 38.332 25.4973 38.5943C25.0419 38.8566 24.5256 38.9947 24 38.9947C23.4744 38.9947 22.9581 38.8566 22.5027 38.5943C22.0472 38.332 21.6687 37.9546 21.405 37.5M39 31.5H9C10.1935 31.5 11.3381 31.0259 12.182 30.182C13.0259 29.3381 13.5 28.1935 13.5 27V19.5C13.5 16.7152 14.6062 14.0445 16.5754 12.0754C18.5445 10.1062 21.2152 9 24 9C26.7848 9 29.4555 10.1062 31.4246 12.0754C33.3938 14.0445 34.5 16.7152 34.5 19.5V27C34.5 28.1935 34.9741 29.3381 35.818 30.182C36.6619 31.0259 37.8065 31.5 39 31.5V31.5Z" stroke="black" stroke-opacity="0.73" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"/></svg>',

            close: '<svg width="10" height="10" viewBox="0 0 10 10" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M9.70711 1.7071C10.0976 1.31659 10.0976 0.683407 9.70711 0.292889C9.31659 -0.0976295 8.68341 -0.0976295 8.29289 0.292889L5 3.58578L1.70711 0.292889C1.31659 -0.0976295 0.68342 -0.0976295 0.292894 0.292889C-0.0976315 0.683407 -0.0976315 1.31659 0.292894 1.7071L3.58579 5L0.292894 8.29289C-0.0976315 8.68341 -0.0976315 9.31659 0.292894 9.7071C0.68342 10.0976 1.31659 10.0976 1.70711 9.7071L5 6.41421L8.29289 9.7071C8.68341 10.0976 9.31659 10.0976 9.70711 9.7071C10.0976 9.31659 10.0976 8.68341 9.70711 8.29289L6.41422 5L9.70711 1.7071Z" fill="black" fill-opacity="0.37"/></svg>',

            newNotification: '<svg width="41" height="41" viewBox="0 0 41 41" fill="none" xmlns="http://www.w3.org/2000/svg"><g filter="url(#filter0_d)"><circle cx="20.5" cy="16.5" r="16.5" fill="#C08AE1"/></g><path d="M13 21L21 13L29 21" stroke="black" stroke-opacity="0.6" stroke-width="3"/><defs><filter id="filter0_d" x="0" y="0" width="41" height="41" filterUnits="userSpaceOnUse" color-interpolation-filters="sRGB"><feFlood flood-opacity="0" result="BackgroundImageFix"/><feColorMatrix in="SourceAlpha" type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"/><feOffset dy="4"/><feGaussianBlur stdDeviation="2"/><feColorMatrix type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0"/><feBlend mode="normal" in2="BackgroundImageFix" result="effect1_dropShadow"/><feBlend mode="normal" in="SourceGraphic" in2="effect1_dropShadow" result="shape"/></filter></defs></svg>'
        }
    }
})
