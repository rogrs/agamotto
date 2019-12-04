import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LinhasArquivos from './linhas-arquivos';
import LinhasArquivosDetail from './linhas-arquivos-detail';
import LinhasArquivosUpdate from './linhas-arquivos-update';
import LinhasArquivosDeleteDialog from './linhas-arquivos-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LinhasArquivosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LinhasArquivosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LinhasArquivosDetail} />
      <ErrorBoundaryRoute path={match.url} component={LinhasArquivos} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LinhasArquivosDeleteDialog} />
  </>
);

export default Routes;
