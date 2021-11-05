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
    } else if ((insNum.val() <= 999) || (insNum.val() >= 99_999_999)) {
        insNum.attr('class', 'form-control is-invalid');
        $('#invalidInsNum').text('Insurance number must contain from 4 to 8 digits');
    } else {
        $.ajax({
            url: "http://localhost:8080/rest/patients/" + insNum.val()
        }).done(function (patientDto) {
            if ((patientDto == null) || (patientDto.id == null)) {
                incorrectInsNum(insNum);
            } else {
                $('#patientInsuranceNumber').attr('readonly', true);
                $('#patientId').val(patientDto.id);
                $('#patientName').val(patientDto.name).attr('class', 'form-control is-valid');
                $('#address').val(patientDto.address);
                changeButton.attr('disabled', false);
                searchButton.attr('disabled', true);
                saveButton.attr('disabled', false);
                insNum.attr('class', 'form-control is-valid');
            }
        }).fail(function () {
            incorrectInsNum(insNum);
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

function incorrectInsNum(insNum) {
    insNum.attr('class', 'form-control is-invalid');
    $('#invalidInsNum').text('Incorrect Insurance number');
}
