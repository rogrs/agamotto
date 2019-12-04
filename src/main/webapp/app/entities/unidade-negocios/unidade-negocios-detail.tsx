import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './unidade-negocios.reducer';
import { IUnidadeNegocios } from 'app/shared/model/unidade-negocios.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUnidadeNegociosDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UnidadeNegociosDetail extends React.Component<IUnidadeNegociosDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { unidadeNegociosEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="agamottoApp.unidadeNegocios.detail.title">UnidadeNegocios</Translate> [<b>{unidadeNegociosEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="razaoSocial">
                <Translate contentKey="agamottoApp.unidadeNegocios.razaoSocial">Razao Social</Translate>
              </span>
            </dt>
            <dd>{unidadeNegociosEntity.razaoSocial}</dd>
            <dt>
              <span id="nomeEmpresa">
                <Translate contentKey="agamottoApp.unidadeNegocios.nomeEmpresa">Nome Empresa</Translate>
              </span>
            </dt>
            <dd>{unidadeNegociosEntity.nomeEmpresa}</dd>
            <dt>
              <span id="cnpj">
                <Translate contentKey="agamottoApp.unidadeNegocios.cnpj">Cnpj</Translate>
              </span>
            </dt>
            <dd>{unidadeNegociosEntity.cnpj}</dd>
            <dt>
              <span id="inscricaoEstadual">
                <Translate contentKey="agamottoApp.unidadeNegocios.inscricaoEstadual">Inscricao Estadual</Translate>
              </span>
            </dt>
            <dd>{unidadeNegociosEntity.inscricaoEstadual}</dd>
            <dt>
              <span id="empregadora">
                <Translate contentKey="agamottoApp.unidadeNegocios.empregadora">Empregadora</Translate>
              </span>
            </dt>
            <dd>{unidadeNegociosEntity.empregadora}</dd>
          </dl>
          <Button tag={Link} to="/unidade-negocios" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/unidade-negocios/${unidadeNegociosEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ unidadeNegocios }: IRootState) => ({
  unidadeNegociosEntity: unidadeNegocios.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UnidadeNegociosDetail);
