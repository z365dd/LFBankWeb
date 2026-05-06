const formHelper = {
    hide: ids => ids.forEach(id => $(`label[for="${id}"]`).parent().hide()),
    show: ids => ids.forEach(id => $(`label[for="${id}"]`).parent().show())
}