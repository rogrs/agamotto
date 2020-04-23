import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Colaboradores from './colaboradores';
import ColaboradoresDetail from './colaboradores-detail';
import ColaboradoresUpdate from './colaboradores-update';
import ColaboradoresDeleteDialog from './colaboradores-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ColaboradoresDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ColaboradoresUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ColaboradoresUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ColaboradoresDetail} />
      <ErrorBoundaryRoute path={match.url} component={Colaboradores} />
    </Switch>
  </>
);

export default Routes;
