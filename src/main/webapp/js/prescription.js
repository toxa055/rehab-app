if ($('#isActive').text() === 'false') {
    $('#prescriptionCancelButtonLink').attr('class', 'btn btn-outline-danger disabled');
    $('#prescriptionUpdateButtonLink').attr('class', 'btn btn-outline-primary disabled');
}

$('#prescriptionUpdateButtonLink').click(function () {
    alert('All planned events will be cancelled!!!');
})
