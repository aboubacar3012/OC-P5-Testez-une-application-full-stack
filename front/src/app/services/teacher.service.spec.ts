import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { TeacherService } from './teacher.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {SessionService} from "./session.service";

describe('TeacherService', () => {
  let service: TeacherService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule,
      ],
      providers: [TeacherService]
    });
    service = TestBed.inject(TeacherService);
    httpMock = TestBed.inject(HttpTestingController)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  afterEach(() => {
    httpMock.verify();
  })

  it('should get all teachers', () => {
    const teachers = [{ id: 1, name: 'Aboubacar Diallo' }, { id: 2, name: 'Oumar Sow' }];

    service.all().subscribe(data => {
      expect(data).toEqual(teachers);
    });

    const req = httpMock.expectOne(service["pathService"]);
    expect(req.request.method).toEqual('GET');
    req.flush(teachers);
  });

  it('should user details', () => {
    const teacherId = "1";
    const teacher = { id: 1, name: 'Aboubacar Diallo' };

    service.detail(teacherId).subscribe(user => {
      expect(user).toEqual(teacher)
    })

    const req = httpMock.expectOne(`${service["pathService"]}/${teacherId}`);
    expect(req.request.method).toEqual('GET');
    req.flush(teacher);
  })
});
