import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Arquivos from './arquivos';
import ArquivosDetail from './arquivos-detail';
import ArquivosUpdate from './arquivos-update';
import ArquivosDeleteDialog from './arquivos-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArquivosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArquivosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArquivosDetail} />
      <ErrorBoundaryRoute path={match.url} component={Arquivos} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ArquivosDeleteDialog} />
  </>
);

export default Routes;
