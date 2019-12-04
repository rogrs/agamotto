import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cargos.reducer';
import { ICargos } from 'app/shared/model/cargos.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICargosDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CargosDetail extends React.Component<ICargosDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { cargosEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="agamottoApp.cargos.detail.title">Cargos</Translate> [<b>{cargosEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nome">
                <Translate contentKey="agamottoApp.cargos.nome">Nome</Translate>
              </span>
            </dt>
            <dd>{cargosEntity.nome}</dd>
            <dt>
              <Translate contentKey="agamottoApp.cargos.colaborador">Colaborador</Translate>
            </dt>
            <dd>{cargosEntity.colaborador ? cargosEntity.colaborador.nome : ''}</dd>
          </dl>
          <Button tag={Link} to="/cargos" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/cargos/${cargosEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ cargos }: IRootState) => ({
  cargosEntity: cargos.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CargosDetail);
