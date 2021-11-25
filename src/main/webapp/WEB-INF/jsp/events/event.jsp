<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <title>Event</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid" id="wrap">
    <h2>Event for ${event.patientName}</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col" style="display: none">id</th>
            <th scope="col" style="display: none">Patient id</th>
            <th scope="col">Ins.№</th>
            <th scope="col">Patient</th>
            <th scope="col" style="display: none">Nurse id</th>
            <th scope="col">Nurse</th>
            <th scope="col" style="display: none">Prescription id</th>
            <th scope="col">Planned Date/Time</th>
            <th scope="col">State</th>
            <th scope="col" style="display: none">Cure id</th>
            <th scope="col">Cure</th>
            <th scope="col">Type</th>
            <th scope="col">Dose</th>
            <th scope="col">End Date/Time</th>
            <th scope="col">Comment</th>
        </tr>
        </thead>
        <tr class="${event.eventState == 'PLANNED' ? 'active-yellow' :
        event.eventState == 'PERFORMED' ? 'active-green' : 'cancelled-red'}">
            <td style="display: none">${event.id}</td>
            <td style="display: none">${event.patientId}</td>
            <td>${event.patientInsuranceNumber}</td>
            <td>${event.patientName}</td>
            <td style="display: none">${event.nurseId}</td>
            <td>${event.nurseName}</td>
            <td style="display: none">${event.prescriptionId}</td>
            <td>${event.plannedDate} ${event.plannedTime}</td>
            <td>${event.eventState}</td>
            <td style="display: none">${event.cureId}</td>
            <td>${event.cureName}</td>
            <td>${event.cureType}</td>
            <td>${event.dose}</td>
            <td>${event.endDate} ${event.endTime}</td>
            <td>${event.comment}</td>
        </tr>
    </table>
    <div>
        <sec:authorize access="hasRole('NURSE')">
            <a class="btn btn-outline-success" href="/events/choose/${event.id}" role="button"
               id="chooseEventButton">Choose</a>
            <a class="btn btn-outline-dark" href="/events/discard/${event.id}" role="button"
               id="discardEventButton">Discard</a>
            <button type="button" class="btn btn-outline-success" data-bs-toggle="modal"
                    data-bs-target="#performEventModal" id="performEventModalButton">Perform
            </button>
            <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal"
                    data-bs-target="#cancelEventModal" id="cancelEventModalButton">Cancel
            </button>
        </sec:authorize>
    </div>
    <br>
    <button type="reset" class="btn btn-outline-secondary" onclick="window.history.back()">Back</button>
</div>

<div class="modal fade" id="performEventModal" tabindex="-1" aria-labelledby="performEventModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="performEventModalLabel">Do you really want to perform event for
                    ${event.patientName}?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cancel</button>
                <a class="btn btn-outline-primary" href="/events/change/${event.id}?state=performed" role="button">
                    Confirm</a>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="cancelEventModal" tabindex="-1" aria-labelledby="cancelEventModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cancelEventModalLabel">Cancel event</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="mb-3">
                        <label for="commentModal" class="col-form-label">Comment</label>
                        <input type="text" class="form-control" name="commentModal" id="commentModal">
                        <div class="invalid-feedback" id="errorCommentModal"></div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="button" name="submit" id="cancelEventButton" class="btn btn-outline-primary">Confirm
                </button>
            </div>
        </div>
    </div>
</div>
<div id="main"></div>
<jsp:include page="../footer.jsp"/>
<script>
    $('#cancelEventButton').click(function () {
        let comm = $('#commentModal').val();
        if ((comm.length < 8) || (comm.length > 60)) {
            $('#commentModal').attr('class', 'form-control is-invalid');
            $('#errorCommentModal').text('Length must be from 8 to 60 symbols');
        } else {
            $('#cancelEventModal').modal('hide');
            window.location = '/events/change/${event.id}?state=cancelled&comment=' + comm;
        }
    });

    let chooseEventButton = $('#chooseEventButton');
    let discardEventButton = $('#discardEventButton');
    let performEventButton = $('#performEventModalButton');
    let cancelEventButton = $('#cancelEventModalButton');

    if (${event.nurseId == authNurseId}) {
        chooseEventButton.attr('class', 'btn btn-outline-success disabled');
        if (${event.eventState != "PLANNED"}) {
            discardEventButton.attr('class', 'btn btn-outline-dark disabled');
            performEventButton.attr('class', 'btn btn-outline-success disabled');
            cancelEventButton.attr('class', 'btn btn-outline-danger disabled');
        }
    } else if (${event.nurseName == null}) {
        discardEventButton.attr('class', 'btn btn-outline-dark disabled');
        performEventButton.attr('class', 'btn btn-outline-success disabled');
        cancelEventButton.attr('class', 'btn btn-outline-danger disabled');
        if (${event.eventState == "CANCELLED"}) {
            chooseEventButton.attr('class', 'btn btn-outline-success disabled');
        }
    } else if (${event.nurseId != authNurseId}) {
        chooseEventButton.attr('class', 'btn btn-outline-success disabled');
        discardEventButton.attr('class', 'btn btn-outline-dark disabled');
        performEventButton.attr('class', 'btn btn-outline-success disabled');
        cancelEventButton.attr('class', 'btn btn-outline-danger disabled');
    }

    $('#cancelEventModal').on('hidden.bs.modal', function () {
        $('#commentModal').attr('class', 'form-control').val('');
    })
</script>
</body>
</html>
