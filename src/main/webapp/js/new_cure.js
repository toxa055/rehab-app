let newCureButton = $('#cureCreateButton');

newCureButton.click(function () {
    let cureName = $('#name');
    if (cureName.val() === '') {
        cureName.attr('class', 'form-control is-invalid')
        $('#invalidCureNameModal').text('Cure name cannot be empty');
    } else if ((cureName.val().length < 3) || (cureName.val().length > 30)) {
        cureName.attr('class', 'form-control is-invalid')
        $('#invalidCureNameModal').text('Length must be from 3 to 30 symbols');
    } else {
        let cure = {
            name: $('#name').val(),
            cureType: $('#cureTypeModal').val()
        }
        $.ajax({
            url: "http://localhost:8060/rest/cures",
            method: "POST",
            dataType: "json",
            data: cure
        }).done(function (cureDto) {
            if ((cureDto == null) || (cureDto.id == null)) {
                cureAlreadyExists(cureName);
            } else {
                $('#cureName').attr('readonly', true).attr('class', 'form-control is-valid').val(cureDto.name)
                $('#cureId').val(cureDto.id)
                $('#cureType').attr('class', 'form-control is-valid').val(cureDto.cureType);
                changeButton.attr('disabled', false);
                searchButton.attr('disabled', true);
                if ($('#cureType').val() === 'PROCEDURE') {
                    $('#dose').attr('readonly', true).val('According to instruction.');
                } else {
                    $('#dose').attr('readonly', false).val('');
                }
                $('#newCureModal').modal('hide');
                $('#name').val('');
                $('#cureTypeModal').val('MEDICINE');
            }
        }).fail(function () {
            cureAlreadyExists(cureName)
        })
    }
});

function cureAlreadyExists(cureName) {
    cureName.attr('class', 'form-control is-invalid')
    $('#invalidCureNameModal').text('Cure with name ' + cureName.val() + ' already exists.');
}

if ($('#cureType').val() === 'PROCEDURE') {
    $('#dose').attr('readonly', true);
}

$('#newCureModal').on('hidden.bs.modal', function () {
    $('#name').attr('class', 'form-control').val('');
    $('#cureTypeModal').val('MEDICINE');
})
