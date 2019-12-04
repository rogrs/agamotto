import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './colaboradores.reducer';
import { IColaboradores } from 'app/shared/model/colaboradores.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IColaboradoresDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ColaboradoresDetail extends React.Component<IColaboradoresDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { colaboradoresEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="agamottoApp.colaboradores.detail.title">Colaboradores</Translate> [<b>{colaboradoresEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nome">
                <Translate contentKey="agamottoApp.colaboradores.nome">Nome</Translate>
              </span>
            </dt>
            <dd>{colaboradoresEntity.nome}</dd>
            <dt>
              <span id="matricula">
                <Translate contentKey="agamottoApp.colaboradores.matricula">Matricula</Translate>
              </span>
            </dt>
            <dd>{colaboradoresEntity.matricula}</dd>
            <dt>
              <span id="pis">
                <Translate contentKey="agamottoApp.colaboradores.pis">Pis</Translate>
              </span>
            </dt>
            <dd>{colaboradoresEntity.pis}</dd>
            <dt>
              <span id="sexo">
                <Translate contentKey="agamottoApp.colaboradores.sexo">Sexo</Translate>
              </span>
            </dt>
            <dd>{colaboradoresEntity.sexo}</dd>
            <dt>
              <span id="admissao">
                <Translate contentKey="agamottoApp.colaboradores.admissao">Admissao</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={colaboradoresEntity.admissao} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="cpf">
                <Translate contentKey="agamottoApp.colaboradores.cpf">Cpf</Translate>
              </span>
            </dt>
            <dd>{colaboradoresEntity.cpf}</dd>
            <dt>
              <span id="ci">
                <Translate contentKey="agamottoApp.colaboradores.ci">Ci</Translate>
              </span>
            </dt>
            <dd>{colaboradoresEntity.ci}</dd>
            <dt>
              <span id="demissao">
                <Translate contentKey="agamottoApp.colaboradores.demissao">Demissao</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={colaboradoresEntity.demissao} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="atualizado">
                <Translate contentKey="agamottoApp.colaboradores.atualizado">Atualizado</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={colaboradoresEntity.atualizado} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="criacao">
                <Translate contentKey="agamottoApp.colaboradores.criacao">Criacao</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={colaboradoresEntity.criacao} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/colaboradores" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/colaboradores/${colaboradoresEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ colaboradores }: IRootState) => ({
  colaboradoresEntity: colaboradores.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ColaboradoresDetail);
