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
    $.ajax({
        url: "http://localhost:8080/rest/patients/" + $('#patientInsuranceNumber').val()
    }).done(function (patientDto) {
        $('#patientInsuranceNumber').attr('readonly', true);
        $('#patientId').val(patientDto.id);
        $('#patientName').val(patientDto.name);
        $('#address').val(patientDto.address);
        changeButton.attr('disabled', false);
        searchButton.attr('disabled', true);
        saveButton.attr('disabled', false);
    })
})

changeButton.click(function () {
    $('#patientInsuranceNumber').attr('readonly', false).val('')
    $('#patientId').val('');
    $('#patientName').val('');
    $('#address').val('');
    changeButton.attr('disabled', true);
    searchButton.attr('disabled', false);
    saveButton.attr('disabled', true);
})
