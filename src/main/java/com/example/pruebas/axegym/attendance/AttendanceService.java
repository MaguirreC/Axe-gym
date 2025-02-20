package com.example.pruebas.axegym.attendance;

import com.example.pruebas.axegym.client.Client;
import com.example.pruebas.axegym.client.ClientRepository;
import com.example.pruebas.axegym.membership.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    ClientRepository clientRepository;

    public ResponseAttendance registerAttendance(Long clientId){

        Client client = clientRepository.findById(clientId).orElseThrow(()->new RuntimeException("client not found"));

        Attendance attendance = new Attendance();
        attendance.setClient(client);
        attendance.setDateTime(LocalDateTime.now());

        Attendance savedAttendance= attendanceRepository.save(attendance);
        Membership activeMembership = client.getMemberships().stream()
                .filter(Membership::getActive)
                .findFirst()
                .orElse(null);

        String membershipPlanName = (activeMembership != null && activeMembership.getMembershipPlan() != null)
                ? activeMembership.getMembershipPlan().getName()
                : "no membership plan";

        Boolean membershipStatus = activeMembership != null;

        return new ResponseAttendance(
                client.getName(),
                membershipStatus,
                membershipPlanName,
                savedAttendance.getDateTime()
        );

    }

    public Page<ResponseAttendance> getAttendances(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return attendanceRepository.findAll(pageable).map(attendance -> {
            Membership activeMembership = attendance.getClient().getMemberships().stream().filter(Membership::getActive)
                    .findFirst().orElse(null);

            String membershipPlanName= (activeMembership != null && activeMembership.getMembershipPlan() != null)
                    ? activeMembership.getMembershipPlan().getName()
                    : "no membership plan";

            Boolean membershipStatus = activeMembership != null;

            return new ResponseAttendance(
                    attendance.getClient().getName(),
                    membershipStatus,
                    membershipPlanName,
                    attendance.getDateTime()
            );

        });
    }
    public Page<ResponseAttendance> getAttendancesByClientId(Long clientId,int page, int size){
        Pageable pageable = PageRequest.of(page,size);

        return attendanceRepository.findAttendanceByClientId(clientId,pageable).map(attendance -> {
            Membership activeMembership = attendance.getClient().getMemberships().stream().filter(Membership::getActive)
                    .findFirst().orElse(null);

            String membershipPlanName= (activeMembership != null && activeMembership.getMembershipPlan() != null)
                    ? activeMembership.getMembershipPlan().getName()
                    : "no membership plan";

            Boolean membershipStatus = activeMembership != null;

            return new ResponseAttendance(
                    attendance.getClient().getName(),
                    membershipStatus,
                    membershipPlanName,
                    attendance.getDateTime()
            );

        });

    }
}
