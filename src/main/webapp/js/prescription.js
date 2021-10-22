if ($('#isActive').text() === 'false') {
    $('#cancelLink').removeAttr('href');
    $('#cancelButton').attr('disabled', true);
    $('#updateLink').removeAttr('href');
    $('#updateButton').attr('disabled', true);
}
let updateButton = $('#updateButton');
updateButton.click(function () {
    alert('All planned events will be cancelled!!!');
})