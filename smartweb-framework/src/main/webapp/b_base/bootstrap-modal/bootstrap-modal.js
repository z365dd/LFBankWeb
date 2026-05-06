const bootstrapModal = {

    create: (modalId, width, height, title) => {
        const modalDiv = `
            <div id="${modalId}" class="modal fade" tabindex="-1" role="dialog">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">${title}</h4>
                  </div>
                  <div class="modal-body">
                    <p>One fine body&hellip;</p>
                  </div>
                  <div class="modal-footer">
                     <button type='button' class='btn btn-primary previous-page hidden'>
                            上一步
                        </button>
                        <button type="button" class="btn btn-primary next-page">
                            下一步
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            取消
                        </button>
                        <label class="page-index" hidden>0</label>
                  </div>
                </div>
              </div>
            </div>
            `;
        $('body').append(modalDiv);
        const $modal = $(`#${modalId}`);

        if ("" !== width) {
            $(`#${modalId} .modal-dialog`).width(width);
        }
        if ("" !== height) {
            $(`#${modalId} .modal-body`).height(height);
        }

        $modal.on('hidden.bs.modal', function () {
            $(`#${modalId}`).remove();
            /* $(`#${modalId} .modal-body`).empty();
             $modal.css("display", "none");*/
        });
        return $modal;
    },

    /**
     * 控制步骤切换
     * @param modalSelect modal选择符，本例为“#myModal”
     * @param modalDataArray html代码字符串数组
     * @param currentPageIndex 当前页码
     * @param direction 1表示下一步方向，-1表示上一步方向
     */
    controlPageToggle: (modalSelect, modalDataArray, currentPageIndex, direction) => {
        if (!yPortion(modalSelect.replace("#", ""))) {
            return;
        }
        const arrayLength = modalDataArray.length;
        if (arrayLength <= 1)
            return;

        if (direction === 1) {
            // 如果当前页是倒数第二页，则将下一步设置为尾页
            if (currentPageIndex === arrayLength - 2) {
                $(`${modalSelect} .next-page`).text("提交");
            }

            // 如果当前页是首页，添加上一步
            if (currentPageIndex === 0 && arrayLength > 1) {
                $(`${modalSelect} .previous-page`).removeClass("hidden");
            }
            // 更换modal-body和页码
            $(`${modalSelect} .page-index`).text((++currentPageIndex).toString());
            $(`${modalSelect} .modal-body`).html(modalDataArray[currentPageIndex].htmlString);
        } else {
            // 如果当前页是尾页
            if (currentPageIndex === arrayLength - 1) {
                $(`${modalSelect} .next-page`).text("下一步");
            }

            // 如果当前页是正数第二页,则将上一步设置为首页
            if (currentPageIndex === 1) {
                $(`${modalSelect} .previous-page`).addClass("hidden");
            }
            // 更换modal-body和页码
            $(`${modalSelect} .page-index`).text((--currentPageIndex).toString());
            $(`${modalSelect} .modal-body`).html(modalDataArray[currentPageIndex].htmlString);
        }
        bootstrapModal.width(modalSelect, modalDataArray[currentPageIndex].width);
        bootstrapModal.height(modalSelect, modalDataArray[currentPageIndex].height);
        if (typeof modalDataArray[currentPageIndex].onReady === "function") {
            modalDataArray[currentPageIndex].onReady();
        }
        bootstrapModal.updateTitle(modalSelect, modalDataArray[currentPageIndex].title);
    },

    /**
     * 对Modal模态框进行初始化操作，包括指定Modal选择符，填充表单元素，绑定步骤切换事件等。
     * @param modalSelect modal选择符
     * @param modalDataArray html代码字符串数组
     * @param onSubmit 提交操作
     */
    modalDataControl: (modalSelect, modalDataArray, onSubmit) => {
        // 初始化模态框首页数据
        $(`${modalSelect} .modal-body`).html(modalDataArray[0].htmlString);
        if (modalDataArray.length <= 1) {
            $(`${modalSelect} .next-page`).text("提交");
        }

        if (parseInt($(`${modalSelect} .page-index`).text()) === 0) {
            bootstrapModal.updateTitle(modalSelect, modalDataArray[0].title);
            if (typeof modalDataArray[0].onReady === "function") {
                modalDataArray[0].onReady();
            }
        }

        // 点击下一步
        $(`${modalSelect} .next-page`).click(function () {
            if ($(this).text() === "提交") {
                if (proof()) {
                    if (typeof onSubmit === "function") {
                        onSubmit();
                    }
                    $(modalSelect).modal('hide');
                }
                return;
            }
            // 获取当前页码
            const currentPageIndex = parseInt($(`${modalSelect} .page-index`).text());
            // 调用回调函数
            if (typeof modalDataArray[currentPageIndex].onPageTurning === "function") {
                modalDataArray[currentPageIndex].onPageTurning();
            }
            bootstrapModal.controlPageToggle(modalSelect, modalDataArray, currentPageIndex, 1);
        });

        // 点击上一步
        $(`${modalSelect} .previous-page`).click(() => {
            //获取当前页码
            const currentPageIndex = parseInt($(`${modalSelect} .page-index`).text());
            // 调用回调函数
            if (typeof modalDataArray[currentPageIndex].onPageTurning === "function") {
                modalDataArray[currentPageIndex].onPageTurning();
            }
            bootstrapModal.controlPageToggle(modalSelect, modalDataArray, currentPageIndex, -1);
        });

        // 关闭模态框时，重置为首页
        $(modalSelect).on('hidden.bs.modal', () => {
            $(`${modalSelect} .page-index`).text((0).toString());
            if (modalDataArray.length <= 1) {
                $(`${modalSelect} .next-page`).text("提交");
            } else {
                $(`${modalSelect} .next-page`).text("下一步");
            }
            $(`${modalSelect} .modal-body`).html(modalDataArray[0].htmlString);
            $(`${modalSelect} .previous-page`).addClass("hidden");
        });
        // 添加校验
        addValidate();
    },

    /**
     * 显示模态框
     * @param modalSelect 模态框ID选择器 如'#myModal'
     */
    show: modalSelect => {
        $(modalSelect).modal({
            backdrop: "static",
            keyboard: false,
            show: true
        })
    },

    /**
     * 隐藏模态框 模态框ID选择器 如'#myModal'
     * @param modalSelect
     */
    hide: modalSelect => {
        $(modalSelect).modal('hide');
    },

    width: (modalSelect, width) => {
        if ("" !== width) {
            $(`${modalSelect} .modal-dialog`).width(width);
        }
    },

    height: (modalSelect, height) => {
        if ("" !== height) {
            $(`${modalSelect} .modal-body`).height(height);
        }
    },

    updateTitle: (modalSelect, title) => {
        $(`${modalSelect} .modal-header .modal-title`).text(title);
    }
}