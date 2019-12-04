import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './feriados.reducer';
import { IFeriados } from 'app/shared/model/feriados.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFeriadosDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FeriadosDetail extends React.Component<IFeriadosDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { feriadosEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="agamottoApp.feriados.detail.title">Feriados</Translate> [<b>{feriadosEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nome">
                <Translate contentKey="agamottoApp.feriados.nome">Nome</Translate>
              </span>
            </dt>
            <dd>{feriadosEntity.nome}</dd>
            <dt>
              <span id="fixoOuMudaTodoAno">
                <Translate contentKey="agamottoApp.feriados.fixoOuMudaTodoAno">Fixo Ou Muda Todo Ano</Translate>
              </span>
            </dt>
            <dd>{feriadosEntity.fixoOuMudaTodoAno ? 'true' : 'false'}</dd>
            <dt>
              <span id="dataFeriado">
                <Translate contentKey="agamottoApp.feriados.dataFeriado">Data Feriado</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={feriadosEntity.dataFeriado} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="agamottoApp.feriados.unidadeNegocios">Unidade Negocios</Translate>
            </dt>
            <dd>{feriadosEntity.unidadeNegocios ? feriadosEntity.unidadeNegocios.razaoSocial : ''}</dd>
          </dl>
          <Button tag={Link} to="/feriados" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/feriados/${feriadosEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ feriados }: IRootState) => ({
  feriadosEntity: feriados.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FeriadosDetail);
