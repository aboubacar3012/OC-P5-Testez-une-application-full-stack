import {HttpClientModule} from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {RouterTestingModule,} from '@angular/router/testing';
import {expect} from '@jest/globals';
import {SessionService} from '../../../../services/session.service';

import {DetailComponent} from './detail.component';
import {SessionApiService} from "../../services/session-api.service";
import {of} from "rxjs";
import {TeacherService} from "../../../../services/teacher.service";
import {Session} from "../../interfaces/session.interface";
import {Teacher} from "../../../../interfaces/teacher.interface";
import {Router} from "@angular/router";


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let service: SessionService;

  let sessionApiServiceMock: jest.Mocked<SessionApiService>;
  let teacherServiceMock: jest.Mocked<TeacherService>;
  let routerServiceMock: jest.Mocked<Router>;
  let matSnackBarServiceMock: jest.Mocked<MatSnackBar>;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }

  beforeEach(async () => {
     sessionApiServiceMock = {
       detail(id:any){return of({users: []})},
       unParticipate:jest.fn(),
       participate: jest.fn(),
       delete: jest.fn()
     } as unknown as jest.Mocked<SessionApiService>
    teacherServiceMock = {
       detail: jest.fn()
     } as unknown as jest.Mocked<TeacherService>;
    // routerServiceMock = {navigate: jest.fn()} as unknown as jest.Mocked<Router>;
    // matSnackBarServiceMock = {open: jest.fn()} as unknown as jest.Mocked<MatSnackBar>;

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: sessionApiServiceMock },
        { provide: TeacherService, useValue: teacherServiceMock },
        // { provide: Router, useValue: routerServiceMock },
        // { provide: MatSnackBar, useValue: matSnackBarServiceMock },
      ],
    })
      .compileComponents();

    service = TestBed.inject(SessionService);
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;

    sessionApiServiceMock = TestBed.inject(SessionApiService) as jest.Mocked<SessionApiService>
    teacherServiceMock = TestBed.inject(TeacherService) as jest.Mocked<TeacherService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call window.history.back on back()', () => {
    const spy = jest.spyOn(window.history, 'back').mockImplementation(() => {});
    component.back();
    expect(spy).toHaveBeenCalled();
  })

  // it('should fetch session data correctly', () => {
  //   const session = { id: 1, name: 'Test session', teacher_id: 1, users: [1, 2, 3] } as unknown as Session;
  //   const teacher = { id: 1, name: 'Test teacher' } as unknown as Teacher;
  //
  //   sessionApiServiceMock.detail.mockReturnValue(of(session));
  //   teacherServiceMock.detail.mockReturnValue(of(teacher));
  //
  //   component.fetchSession();
  //
  //   expect(sessionApiServiceMock.detail).toHaveBeenCalledWith(component.sessionId);
  //   expect(teacherServiceMock.detail).toHaveBeenCalledWith(session.teacher_id.toString());
  //   expect(component.session).toEqual(session);
  //   expect(component.teacher).toEqual(teacher);
  //   expect(component.isParticipate).toBe(true);
  // });
  //
  //
  // it('should call unParticipate() and fetchSession()', () => {
  //   const sessionId = "1";
  //   const userId = "2";
  //   component.sessionId = sessionId;
  //   component.userId = userId;
  //   fixture.detectChanges();
  //   sessionApiServiceMock.unParticipate.mockReturnValue(of())
  //   component.unParticipate();
  //
  //   expect(component.unParticipate).toHaveBeenCalledWith(sessionId, userId);
  //   expect(component.fetchSession).toHaveBeenCalled();
  // })
  // it('should call participate() and fetchSession()', () => {
  //   const sessionId = "1";
  //   const userId = "2";
  //   component.sessionId = sessionId;
  //   component.userId = userId;
  //   fixture.detectChanges();
  //   sessionApiServiceMock.participate.mockReturnValue(of())
  //   component.participate();
  //
  //   expect(component.participate).toHaveBeenCalledWith(sessionId, userId);
  //   expect(component.fetchSession).toHaveBeenCalled();
  // })
  //
  // it('should delete the session and navigate to sessions page', () => {
  //   sessionApiServiceMock.delete.mockReturnValue(of());
  //   component.sessionId = "1";
  //   fixture.detectChanges();
  //
  //   expect(sessionApiServiceMock.delete).toHaveBeenCalledWith(component.sessionId);
  //   expect(matSnackBarServiceMock.open).toHaveBeenCalledWith('Session deleted !', 'Close', { duration: 3000 })
  //   expect(routerServiceMock.navigate).toHaveBeenCalledWith(["sessions"])
  //
  // })

});

