import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Turnos from './turnos';
import TurnosDetail from './turnos-detail';
import TurnosUpdate from './turnos-update';
import TurnosDeleteDialog from './turnos-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TurnosDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TurnosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TurnosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TurnosDetail} />
      <ErrorBoundaryRoute path={match.url} component={Turnos} />
    </Switch>
  </>
);

export default Routes;
