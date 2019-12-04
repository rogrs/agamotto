import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Feriados from './feriados';
import FeriadosDetail from './feriados-detail';
import FeriadosUpdate from './feriados-update';
import FeriadosDeleteDialog from './feriados-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FeriadosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FeriadosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FeriadosDetail} />
      <ErrorBoundaryRoute path={match.url} component={Feriados} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FeriadosDeleteDialog} />
  </>
);

export default Routes;
