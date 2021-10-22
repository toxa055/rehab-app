if ($('#isActive').text() === 'false') {
    $('#prescriptionCancelButtonLink').attr('class', 'btn btn-danger disabled');
    $('#prescriptionUpdateButtonLink').attr('class', 'btn btn-warning disabled');
}

$('#prescriptionUpdateButtonLink').click(function () {
    alert('All planned events will be cancelled!!!');
})
