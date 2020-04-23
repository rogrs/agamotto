import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './controle-ponto.reducer';
import { IControlePonto } from 'app/shared/model/controle-ponto.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IControlePontoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ControlePontoDetail = (props: IControlePontoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { controlePontoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="agamottoApp.controlePonto.detail.title">ControlePonto</Translate> [<b>{controlePontoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="data">
              <Translate contentKey="agamottoApp.controlePonto.data">Data</Translate>
            </span>
          </dt>
          <dd>{controlePontoEntity.data}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="agamottoApp.controlePonto.status">Status</Translate>
            </span>
          </dt>
          <dd>{controlePontoEntity.status}</dd>
          <dt>
            <Translate contentKey="agamottoApp.controlePonto.colaborador">Colaborador</Translate>
          </dt>
          <dd>{controlePontoEntity.colaborador ? controlePontoEntity.colaborador.nome : ''}</dd>
        </dl>
        <Button tag={Link} to="/controle-ponto" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/controle-ponto/${controlePontoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ controlePonto }: IRootState) => ({
  controlePontoEntity: controlePonto.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ControlePontoDetail);
