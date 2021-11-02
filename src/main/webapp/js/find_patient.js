let searchButton = $('#searchByInsNum');
let changeButton = $('#changeInsNum');
let saveButton = $('#saveButton');

if ($('#patientInsuranceNumber').val() !== '') {
    $('#patientInsuranceNumber').attr('readonly', true);
    searchButton.attr('disabled', true);
    changeButton.attr('disabled', false);
    saveButton.attr('disabled', false);
} else {
    $('#patientInsuranceNumber').attr('readonly', false);
    searchButton.attr('disabled', false);
    changeButton.attr('disabled', true);
    saveButton.attr('disabled', true);
}

searchButton.click(function () {
    let insNum = $('#patientInsuranceNumber');
    if (insNum.val() === '') {
        insNum.attr('class', 'form-control is-invalid');
        $('#invalidInsNum').text('Insurance number cannot be empty');
    } else {
        $.ajax({
            url: "http://localhost:8080/rest/patients/" + insNum.val()
        }).done(function (patientDto) {
            $('#patientInsuranceNumber').attr('readonly', true);
            $('#patientId').val(patientDto.id);
            $('#patientName').val(patientDto.name).attr('class', 'form-control is-valid');
            $('#address').val(patientDto.address);
            changeButton.attr('disabled', false);
            searchButton.attr('disabled', true);
            saveButton.attr('disabled', false);
            insNum.attr('class', 'form-control is-valid');
        }).fail(function () {
            insNum.attr('class', 'form-control is-invalid');
            $('#invalidInsNum').text('Incorrect Insurance number');
        })
    }
})

changeButton.click(function () {
    $('#patientInsuranceNumber').attr('readonly', false).attr('class', 'form-control').val('')
    $('#patientId').val('');
    $('#patientName').val('').attr('class', 'form-control');
    $('#address').val('');
    changeButton.attr('disabled', true);
    searchButton.attr('disabled', false);
    saveButton.attr('disabled', true);
})
