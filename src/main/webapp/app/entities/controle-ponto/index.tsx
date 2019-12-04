import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ControlePonto from './controle-ponto';
import ControlePontoDetail from './controle-ponto-detail';
import ControlePontoUpdate from './controle-ponto-update';
import ControlePontoDeleteDialog from './controle-ponto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ControlePontoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ControlePontoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ControlePontoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ControlePonto} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ControlePontoDeleteDialog} />
  </>
);

export default Routes;
