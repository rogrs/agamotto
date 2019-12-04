import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UnidadeNegocios from './unidade-negocios';
import Feriados from './feriados';
import Departamentos from './departamentos';
import Equipes from './equipes';
import Cargos from './cargos';
import Turnos from './turnos';
import Colaboradores from './colaboradores';
import Arquivos from './arquivos';
import LinhasArquivos from './linhas-arquivos';
import ControlePonto from './controle-ponto';
import Ponto from './ponto';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}unidade-negocios`} component={UnidadeNegocios} />
      <ErrorBoundaryRoute path={`${match.url}feriados`} component={Feriados} />
      <ErrorBoundaryRoute path={`${match.url}departamentos`} component={Departamentos} />
      <ErrorBoundaryRoute path={`${match.url}equipes`} component={Equipes} />
      <ErrorBoundaryRoute path={`${match.url}cargos`} component={Cargos} />
      <ErrorBoundaryRoute path={`${match.url}turnos`} component={Turnos} />
      <ErrorBoundaryRoute path={`${match.url}colaboradores`} component={Colaboradores} />
      <ErrorBoundaryRoute path={`${match.url}arquivos`} component={Arquivos} />
      <ErrorBoundaryRoute path={`${match.url}linhas-arquivos`} component={LinhasArquivos} />
      <ErrorBoundaryRoute path={`${match.url}controle-ponto`} component={ControlePonto} />
      <ErrorBoundaryRoute path={`${match.url}ponto`} component={Ponto} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
