describe("Sessions spec", () => {
  beforeEach(() => {
    cy.visit('/login')

    cy.intercept({
        method: 'POST',
        url: '/api/auth/login',
      },
      {
        body: {
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: true,
          token:"token"
        },
      },[]).as("login")

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      {
        body:[{"id":1,"name":"Session essaie","date":"2023-03-24T00:00:00.000+00:00","teacher_id":1,"description":"Apprennez les bases du yoga Apprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yoga","users":[2,3],"createdAt":"2023-03-23T17:22:01","updatedAt":"2023-03-23T17:29:21"},{"id":3,"name":"session du soir","date":"2023-03-24T00:00:00.000+00:00","teacher_id":1,"description":"session du soir session du soir session du soir session du soir session du soir session du soir","users":[],"createdAt":"2023-03-23T17:24:33","updatedAt":"2023-03-23T17:24:33"}]
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.url().should('include',"/sessions")

  })
  it('show sessions', () => {

    // L’apparition des boutons Create et Detail si
    // l’utilisateur connecté est un admin
    cy.get(".mat-card-header").find("button").contains("Create")
    cy.get(".mat-card-actions").eq(0).find("button").eq(0).contains("Detail")
    cy.get(".mat-card-actions").eq(0).find("button").eq(1).contains("Edit")
  })

  it('session information', () => {

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session/1',
      },
      (req) => {
        expect(req.headers.authorization).to.include('Bearer');
        expect(req.headers.authorization).to.include('token');
        req.reply({
          body:{"id":1,"name":"Session essaie","date":"2023-03-24T00:00:00.000+00:00","teacher_id":1,"description":"Apprennez les bases du yoga ","users":[2,3],"createdAt":"2023-03-23T17:22:01","updatedAt":"2023-03-23T17:29:21"},
          statusCode: 200,
        });
      },
    ).as('getSession');
    cy.intercept(
      {
        method: 'GET',
        url: '/api/teacher/1',
      },
      (req) => {
        expect(req.headers.authorization).to.include('Bearer');
        expect(req.headers.authorization).to.include('token');
        req.reply({
          body:{"id":1,"lastName":"DELAHAYE","firstName":"Margot","createdAt":"2023-03-23T16:58:03","updatedAt":"2023-03-23T16:58:03"},
          statusCode: 200,
        });
      },
    ).as('getTeacher');

    cy.get(".mat-card-actions").eq(0).find("button").eq(0).click()

    cy.url().should('include', '/sessions/detail/1');

    cy.get(".ml1").eq(0).contains("Delete")
  })

  it('session creation', () => {
    const date = '30/04/2023';
    const formattedDate = `${date.slice(6)}-${date.slice(3,5)}-${date.slice(0,2)}`; // Convertit la date en format YYYY-MM-DD

    cy.intercept(
      {
        method: 'GET',
        url: '/api/teacher',
      },
      (req) => {
        expect(req.headers.authorization).to.include('Bearer');
        expect(req.headers.authorization).to.include('token');
        req.reply({
          body:[{"id":1,"lastName":"DELAHAYE","firstName":"Margot","createdAt":"2023-03-23T16:58:03","updatedAt":"2023-03-23T16:58:03"},{"id":2,"lastName":"THIERCELIN","firstName":"Hélène","createdAt":"2023-03-23T16:58:03","updatedAt":"2023-03-23T16:58:03"}],
          statusCode: 200,
        });
      },
    ).as('getTeacher');

    cy.intercept(
      {
        method: 'POST',
        url: '/api/session',
      },
      {
        body:[
          {"id":4,"name":"Cours de yoga pour debutant","date":new Date(formattedDate).toISOString(),"teacher_id":1,"description":"Description","users":[],"createdAt":"2023-04-14T10:45:53","updatedAt":"2023-04-14T10:45:53"}]
      },
      []).as('session')


    cy.get(".mat-card-header").find("button").click()
    cy.get('input[formControlName=name]').type("Cours de yoga pour debutant")
    cy.get('input[formControlName=date]').type(formattedDate)
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('.mat-select-panel-wrap').find("span").eq(0).click()
    cy.get('textarea[formControlName=description]').type("Description")
    cy.get('form').submit()
  })

  it('delete session', () => {
    cy.intercept(
      {
        method: 'GET',
        url: '/api/session/1',
      },
      (req) => {
        expect(req.headers.authorization).to.include('Bearer');
        expect(req.headers.authorization).to.include('token');
        req.reply({
          body:{"id":1,"name":"Session essaie","date":"2023-03-24T00:00:00.000+00:00","teacher_id":1,"description":"Apprennez les bases du yoga ","users":[2,3],"createdAt":"2023-03-23T17:22:01","updatedAt":"2023-03-23T17:29:21"},
          statusCode: 200,
        });
      },
    ).as('getSession');
    cy.intercept(
      {
        method: 'GET',
        url: '/api/teacher/1',
      },
      (req) => {
        expect(req.headers.authorization).to.include('Bearer');
        expect(req.headers.authorization).to.include('token');
        req.reply({
          body:{"id":1,"lastName":"DELAHAYE","firstName":"Margot","createdAt":"2023-03-23T16:58:03","updatedAt":"2023-03-23T16:58:03"},
          statusCode: 200,
        });
      },
    ).as('getTeacher');

    cy.intercept(
      {
        method: 'DELETE',
        url: '/api/session/1',
      },
      (req) => {
        req.reply({statusCode:200})
      }
    ).as('deleteSession');

    cy.get(".mat-card-actions").eq(0).find("button").eq(0).click()

    cy.url().should('include', '/sessions/detail/1');

    cy.get(".ml1").eq(0).click()
  })

  it('update session', () => {
    const date = '30/04/2023';
    const formattedDate = `${date.slice(6)}-${date.slice(3,5)}-${date.slice(0,2)}`;
    cy.intercept(
      {
        method: 'GET',
        url: '/api/session/1',
      },
      (req) => {
        expect(req.headers.authorization).to.include('Bearer');
        expect(req.headers.authorization).to.include('token');
        req.reply({
          body:{"id":1,"name":"Session essaie","date":"2023-03-24T00:00:00.000+00:00","teacher_id":1,"description":"Apprennez les bases du yoga ","users":[2,3],"createdAt":"2023-03-23T17:22:01","updatedAt":"2023-03-23T17:29:21"},
          statusCode: 200,
        });
      },
    ).as('getSession');
    cy.intercept(
      {
        method: 'GET',
        url: '/api/teacher',
      },
      (req) => {
        expect(req.headers.authorization).to.include('Bearer');
        expect(req.headers.authorization).to.include('token');
        req.reply({
          body:[{"id":1,"lastName":"DELAHAYE","firstName":"Margot","createdAt":"2023-03-23T16:58:03","updatedAt":"2023-03-23T16:58:03"},{"id":2,"lastName":"THIERCELIN","firstName":"Hélène","createdAt":"2023-03-23T16:58:03","updatedAt":"2023-03-23T16:58:03"}],
          statusCode: 200,
        });
      },
    ).as('getTeacher');

    cy.intercept(
      {
        method: 'PUT',
        url: '/api/session/1',
      },
      (req) => {
        req.reply({statusCode:200})
      }
    ).as('updateSession');

    cy.get(".mat-card-actions").eq(0).find("button").eq(1).click()

    cy.url().should('include', '/sessions/update/1');


    cy.get('input[formControlName=name]').type(" (updated)")
    cy.get('input[formControlName=date]').type(formattedDate)
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('.mat-select-panel-wrap').find("span").eq(1).click()
    cy.get('textarea[formControlName=description]').type(" (updated)")
    cy.get('form').submit()

  })
})
