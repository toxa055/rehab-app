if ($('#isActive').text() === 'false') {
    $('#prescriptionCancelButton').attr('disabled', 'true');
    $('#prescriptionUpdateButton').attr('disabled', 'true');
}

let pattern = $('#pattern').text();
$('#pattern').text(pattern.replace('; )', ')'));
